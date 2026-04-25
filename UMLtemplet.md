@startuml
abstract class TowerFloor {
-floorName : String

+explore(party : List<Hero>) : FloorResult
#announce() : void
#setup(party : List<Hero>) : void
#resolveChallenge(party : List<Hero>) : FloorResult
#shouldAwardLoot(result : FloorResult) : boolean
#awardLoot(party : List<Hero>, result : FloorResult) : void
#cleanup(party : List<Hero>) : void
}

class CombatFloor {
-monster : Monster
#setup(party : List<Hero>) : void
#resolveChallenge(party : List<Hero>) : FloorResult
#awardLoot(party : List<Hero>, result : FloorResult) : void
}

class TrapFloor {
#setup(party : List<Hero>) : void
#resolveChallenge(party : List<Hero>) : FloorResult
#awardLoot(party : List<Hero>, result : FloorResult) : void
}

class BossFloor {
#announce() : void
#cleanup(party : List<Hero>) : void
}

class FloorResult {
-cleared : boolean
-damageTaken : int
-summary : String

+isCleared() : boolean
+getDamageTaken() : int
+getSummary() : String
}

TowerFloor <|-- CombatFloor
TowerFloor <|-- TrapFloor
CombatFloor <|-- BossFloor

TowerFloor --> FloorResult : returns
CombatFloor --> Monster : uses
TowerFloor --> Hero : uses party

note right of TowerFloor
explore() is the final template method:
announce -> setup -> resolveChallenge
-> shouldAwardLoot -> awardLoot -> cleanup
end note

@enduml