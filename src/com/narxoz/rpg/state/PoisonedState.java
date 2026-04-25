package com.narxoz.rpg.state;

import com.narxoz.rpg.combatant.Hero;

public class PoisonedState implements HeroState {

    private int turnsRemaining;

    public PoisonedState(int turns) {
        this.turnsRemaining = turns;
    }

    @Override
    public String getName() { return "Poisoned"; }

    @Override
    public int modifyOutgoingDamage(int basePower) { return (int)(basePower * 0.8); }

    @Override
    public int modifyIncomingDamage(int rawDamage) { return rawDamage; }

    @Override
    public void onTurnStart(Hero hero) {
        System.out.println(hero.getName() + " suffers 5 poison damage!");
        hero.takeDamage(5);
    }

    @Override
    public void onTurnEnd(Hero hero) {
        turnsRemaining--;
        if (turnsRemaining <= 0 && hero.getState() == this) {
            System.out.println(hero.getName() + "'s poison wears off!");
            hero.setState(new NormalState());
        }
    }

    @Override
    public boolean canAct() { return true; }
}