package com.narxoz.rpg.floor;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.combatant.Monster;
import java.util.List;

public class CombatFloor extends TowerFloor {

    private final Monster monster;
    private int totalDamageTaken;

    public CombatFloor(Monster monster) {
        this.monster = monster;
    }

    @Override
    protected String getFloorName() { return "Combat Floor: " + monster.getName(); }

    @Override
    protected void setup(List<Hero> party) {
        totalDamageTaken = 0;
        System.out.println("A wild " + monster.getName() + " appears! HP: " + monster.getHp());
    }

    @Override
    protected FloorResult resolveChallenge(List<Hero> party) {
        int round = 1;
        while (monster.isAlive() && party.stream().anyMatch(Hero::isAlive)) {
            System.out.println("[Round " + round + "]");
            for (Hero hero : party) {
                if (!hero.isAlive()) continue;
                hero.startTurn();
                if (hero.canAct()) {
                    int dmg = hero.calculateAttack();
                    monster.takeDamage(dmg);
                    System.out.println("  " + hero.getName() + " attacks for " + dmg
                            + ". Monster HP: " + monster.getHp());
                }
                if (!monster.isAlive()) break;
                int monsterDmg = monster.getAttackPower();
                hero.takeDamage(monsterDmg);
                totalDamageTaken += monsterDmg;
                hero.endTurn();
            }
            round++;
            if (round > 20) break;
        }
        boolean cleared = !monster.isAlive();
        String summary = cleared
                ? monster.getName() + " defeated!"
                : "Party wiped out by " + monster.getName();
        System.out.println(summary);
        return new FloorResult(cleared, totalDamageTaken, summary);
    }

    @Override
    protected void awardLoot(List<Hero> party, FloorResult result) {
        System.out.println("The party finds a small pouch of gold.");
    }
}