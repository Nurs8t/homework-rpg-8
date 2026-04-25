@startuml

interface HeroState {
+getName() : String
+modifyOutgoingDamage(basePower : int) : int
+modifyIncomingDamage(rawDamage : int) : int
+onTurnStart(hero : Hero) : void
+onTurnEnd(hero : Hero) : void
+canAct() : boolean
}

class Hero {
-name : String
-maxHp : int
-hp : int
-attackPower : int
-defense : int
-state : HeroState

+getName() : String
+getMaxHp() : int
+getState() : HeroState
+setState(state : HeroState) : void
+takeDamage(amount : int) : void
+attack(monster : Monster) : void
}

class NormalState
class PoisonedState {
-turnsRemaining : int
}
class StunnedState {
-turnsRemaining : int
}
class BerserkState

Hero --> HeroState : has current state

HeroState <|.. NormalState
HeroState <|.. PoisonedState
HeroState <|.. StunnedState
HeroState <|.. BerserkState

PoisonedState ..> NormalState : transitions to
StunnedState ..> NormalState : transitions to
NormalState ..> BerserkState : may transition to

@enduml