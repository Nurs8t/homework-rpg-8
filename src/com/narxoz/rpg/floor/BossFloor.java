package com.narxoz.rpg.floor;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.combatant.Monster;
import com.narxoz.rpg.state.StunnedState;
import java.util.List;

public class BossFloor extends TowerFloor {

    private final Monster boss;
    private int totalDamageTaken;

    public BossFloor(Monster boss) {
        this.boss = boss;
    }

    @Override
    protected String getFloorName() { return "Boss Chamber: " + boss.getName(); }

    @Override
    protected void announce() {
        System.out.println("\n*** BOSS FLOOR ***");
        System.out.println("*** " + boss.getName() + " awakens! Prepare for the fight of your lives! ***");
    }

    @Override
    protected void setup(List<Hero> party) {
        totalDamageTaken = 0;
        System.out.println(boss.getName() + " lets out a deafening roar! Boss HP: " + boss.getHp());
        System.out.println("The shockwave stuns the entire party for one turn!");
        for (Hero hero : party) {
            if (hero.isAlive()) hero.setState(new StunnedState());
        }
    }

    @Override
    protected FloorResult resolveChallenge(List<Hero> party) {
        int round = 1;
        while (boss.isAlive() && party.stream().anyMatch(Hero::isAlive)) {
            System.out.println("[Boss Round " + round + "]");
            for (Hero hero : party) {
                if (!hero.isAlive()) continue;
                hero.startTurn();
                if (hero.canAct()) {
                    int dmg = hero.calculateAttack();
                    boss.takeDamage(dmg);
                    System.out.println("  " + hero.getName() + " strikes boss for " + dmg
                            + ". Boss HP: " + boss.getHp());
                }
                if (!boss.isAlive()) break;
                int bossDmg = boss.getAttackPower() + 5;
                hero.takeDamage(bossDmg);
                totalDamageTaken += bossDmg;
                hero.endTurn();
            }
            round++;
            if (round > 25) break;
        }
        boolean cleared = !boss.isAlive();
        String summary = cleared
                ? boss.getName() + " has been slain! VICTORY!"
                : "The party fell before " + boss.getName() + "...";
        System.out.println(summary);
        return new FloorResult(cleared, totalDamageTaken, summary);
    }

    @Override
    protected boolean shouldAwardLoot(FloorResult result) {
        return result.isCleared();
    }

    @Override
    protected void awardLoot(List<Hero> party, FloorResult result) {
        System.out.println("Legendary loot claimed! All heroes fully restored!");
        for (Hero hero : party) {
            if (hero.isAlive()) hero.heal(hero.getMaxHp());
        }
    }

    @Override
    protected void cleanup(List<Hero> party) {
        System.out.println("The boss chamber collapses. The party races to safety.");
    }
}