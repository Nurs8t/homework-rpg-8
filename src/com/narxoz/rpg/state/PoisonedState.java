package com.narxoz.rpg.state;

import com.narxoz.rpg.combatant.Hero;

public class PoisonedState implements HeroState {
    private int turnsRemaining;

    public PoisonedState(int turnsRemaining) {
        this.turnsRemaining = turnsRemaining;
    }

    @Override
    public String getName() {
        return "Poisoned(" + turnsRemaining + ")";
    }

    @Override
    public int modifyOutgoingDamage(int basePower) {
        return Math.max(1, basePower - 3);
    }

    @Override
    public int modifyIncomingDamage(int rawDamage) {
        return rawDamage + 2;
    }

    @Override
    public void onTurnStart(Hero hero) {
        System.out.println(hero.getName() + " suffers poison damage.");

        hero.takeDamage(4);
        turnsRemaining--;

        if (turnsRemaining <= 0 && hero.isAlive()) {
            hero.setState(new NormalState());
        }
    }

    @Override
    public void onTurnEnd(Hero hero) {
    }

    @Override
    public boolean canAct() {
        return true;
    }
}