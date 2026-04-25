package com.narxoz.rpg.tower;


import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.floor.FloorResult;
import com.narxoz.rpg.floor.TowerFloor;
import java.util.List;

public class TowerRunner {

    private final List<Hero> party;
    private final List<TowerFloor> floors;

    public TowerRunner(List<Hero> party, List<TowerFloor> floors) {
        this.party = party;
        this.floors = floors;
    }

    public TowerRunResult run() {
        int floorsCleared = 0;
        for (TowerFloor floor : floors) {
            if (party.stream().noneMatch(Hero::isAlive)) break;
            FloorResult result = floor.explore(party);
            if (result.isCleared()) {
                floorsCleared++;
            } else {
                System.out.println("The party could not clear the floor. Tower run ends.");
                break;
            }
        }
        int surviving = (int) party.stream().filter(Hero::isAlive).count();
        boolean reachedTop = floorsCleared == floors.size();
        return new TowerRunResult(floorsCleared, surviving, reachedTop);
    }
}