package com.narxoz.rpg.state;

import com.narxoz.rpg.combatant.Hero;

public class StunnedState implements HeroState {
    private int turnsRemaining;

    public StunnedState(int turnsRemaining) {
        this.turnsRemaining = turnsRemaining;
    }

    @Override
    public String getName() {
        return "Stunned(" + turnsRemaining + ")";
    }

    @Override
    public int modifyOutgoingDamage(int basePower) {
        return basePower;
    }

    @Override
    public int modifyIncomingDamage(int rawDamage) {
        return rawDamage;
    }

    @Override
    public void onTurnStart(Hero hero) {
        System.out.println(hero.getName() + " is stunned and cannot act.");
        turnsRemaining--;
        if (turnsRemaining <= 0 && hero.isAlive()) {
        }
    }

    @Override
    public void onTurnEnd(Hero hero) {
    }

    @Override
    public boolean canAct() {
        return false;
    }
}