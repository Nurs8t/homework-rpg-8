package com.narxoz.rpg.combatant;

import com.narxoz.rpg.state.BerserkState;
import com.narxoz.rpg.state.HeroState;
import com.narxoz.rpg.state.NormalState;

public class Hero {

    private final String name;
    private int hp;
    private final int maxHp;
    private final int attackPower;
    private final int defense;
    private HeroState state;

    public Hero(String name, int hp, int attackPower, int defense) {
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.attackPower = attackPower;
        this.defense = defense;
        this.state = new NormalState();
    }

    public String getName()        { return name; }
    public int getHp()             { return hp; }
    public int getMaxHp()          { return maxHp; }
    public int getAttackPower()    { return attackPower; }
    public int getDefense()        { return defense; }
    public boolean isAlive()       { return hp > 0; }
    public HeroState getState()    { return state; }

    public void setState(HeroState newState) {
        System.out.println("  [State] " + name + ": " + state.getName() + " -> " + newState.getName());
        this.state = newState;
    }

    public void takeDamage(int amount) {
        int actual = state.modifyIncomingDamage(amount);
        hp = Math.max(0, hp - actual);
        System.out.println("  " + name + " takes " + actual + " damage. HP: " + hp + "/" + maxHp);
        if (isAlive() && hp <= maxHp * 0.3 && !(state instanceof BerserkState)) {
            System.out.println("  " + name + " is critically wounded!");
            setState(new BerserkState());
        }
    }

    public void heal(int amount) {
        hp = Math.min(maxHp, hp + amount);
        System.out.println("  " + name + " heals " + amount + " HP. HP: " + hp + "/" + maxHp);
    }

    public int calculateAttack() {
        return state.modifyOutgoingDamage(attackPower);
    }

    public void startTurn() {
        state.onTurnStart(this);
    }

    public void endTurn() {
        state.onTurnEnd(this);
    }

    public boolean canAct() {
        return state.canAct();
    }
}