package com.narxoz.rpg.floor;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.state.PoisonedState;
import java.util.List;

public class TrapFloor extends TowerFloor {

    @Override
    protected String getFloorName() { return "Poison Trap Chamber"; }

    @Override
    protected void announce() {
        System.out.println("\n=== DANGER: " + getFloorName() + " ===");
        System.out.println("The air reeks of venom. Dart traps line every wall...");
    }

    @Override
    protected void setup(List<Hero> party) {
        System.out.println("The party moves cautiously through the chamber.");
    }

    @Override
    protected FloorResult resolveChallenge(List<Hero> party) {
        int damageTaken = 0;
        for (Hero hero : party) {
            if (!hero.isAlive()) continue;
            System.out.println(hero.getName() + " triggers a poison dart!");
            hero.setState(new PoisonedState(3));
            hero.takeDamage(8);
            damageTaken += 8;
        }
        String summary = "Party stumbles through the poison trap chamber.";
        System.out.println(summary);
        return new FloorResult(true, damageTaken, summary);
    }

    @Override
    protected boolean shouldAwardLoot(FloorResult result) {
        return result.isCleared();
    }

    @Override
    protected void awardLoot(List<Hero> party, FloorResult result) {
        System.out.println("Hidden chest found! +15 HP to all surviving heroes.");
        for (Hero hero : party) {
            if (hero.isAlive()) hero.heal(15);
        }
    }
}