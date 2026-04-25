package com.narxoz.rpg;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.combatant.Monster;
import com.narxoz.rpg.floor.BossFloor;
import com.narxoz.rpg.floor.CombatFloor;
import com.narxoz.rpg.floor.TowerFloor;
import com.narxoz.rpg.floor.TrapFloor;
import com.narxoz.rpg.state.PoisonedState;
import com.narxoz.rpg.tower.TowerRunResult;
import com.narxoz.rpg.tower.TowerRunner;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        Hero warrior = new Hero("Warrior", 100, 20, 8);
        Hero mage = new Hero("Mage", 70, 30, 3);

        mage.setState(new PoisonedState(2));

        List<Hero> party = List.of(warrior, mage);

        List<TowerFloor> floors = List.of(
                new CombatFloor(new Monster("Skeleton", 40, 12)),
                new TrapFloor(),
                new CombatFloor(new Monster("Stone Golem", 70, 18)),
                new BossFloor(new Monster("Shadow Dragon", 120, 25))
        );

        System.out.println("=== THE HAUNTED TOWER ===");
        System.out.println("Party:");
        System.out.println("- " + warrior.getName() + " HP: " + warrior.getMaxHp()
                + " State: " + warrior.getState().getName());
        System.out.println("- " + mage.getName() + " HP: " + mage.getMaxHp()
                + " State: " + mage.getState().getName());

        TowerRunner runner = new TowerRunner(party, floors);
        TowerRunResult result = runner.run();

        System.out.println("\n=== TOWER RUN COMPLETE ===");
        System.out.println("Floors cleared  : " + result.getFloorsCleared());
        System.out.println("Heroes surviving: " + result.getHeroesSurviving());
        System.out.println("Tower conquered : " + result.isReachedTop());
    }
}