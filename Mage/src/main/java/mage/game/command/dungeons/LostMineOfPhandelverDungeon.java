package mage.game.command.dungeons;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.constants.Duration;
import mage.counters.CounterType;
import mage.game.command.Dungeon;
import mage.game.command.DungeonRoom;
import mage.game.permanent.token.GoblinToken;
import mage.game.permanent.token.TreasureToken;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author TheElk801
 */
public class LostMineOfPhandelverDungeon extends Dungeon {

    public LostMineOfPhandelverDungeon() {
        super("Lost Mine of Phandelver");
        // (1) Cave Entrance — Scry 1. (→ 2a or 2b)
        DungeonRoom caveEntrance = new DungeonRoom(
                "Cave Entrance", new ScryEffect(1, false)
        );

        // (2a) Goblin Lair — Create a 1/1 red Goblin creature token. (→ 3a or 3b)
        DungeonRoom goblinLair = new DungeonRoom("Goblin Lair", new CreateTokenEffect(new GoblinToken()));

        // (2b) Mine Tunnels — Create a Treasure token. (→ 3b or 3c)
        DungeonRoom mineTunnels = new DungeonRoom("Mine Tunnels", new CreateTokenEffect(new TreasureToken()));

        // (3a) Storeroom — Put a +1/+1 counter on target creature. (→ 4)
        DungeonRoom storeroom = new DungeonRoom(
                "Storeroom", new AddCountersTargetEffect(CounterType.P1P1.createInstance())
        );
        storeroom.addTarget(new TargetCreaturePermanent());

        // (3b) Dark Pool — Each opponent loses 1 life and you gain 1 life. (→ 4)
        DungeonRoom darkPool = new DungeonRoom(
                "Dark Pool", new LoseLifeOpponentsEffect(1),
                new GainLifeEffect(1).concatBy("and")
        );

        // (3c) Fungi Cavern — Target creature gets -4/-0 until your next turn. (→ 4)
        DungeonRoom fungiCavern = new DungeonRoom(
                "Fungi Cavern", new BoostTargetEffect(-4, 0, Duration.UntilYourNextTurn)
        );
        fungiCavern.addTarget(new TargetCreaturePermanent());

        // (4) Temple of Dumathoin — Draw a card.
        DungeonRoom templeOfDumathoin = new DungeonRoom(
                "Temple of Dumathoin", new DrawCardSourceControllerEffect(1)
        );

        caveEntrance.addNextRoom(goblinLair);
        caveEntrance.addNextRoom(mineTunnels);
        goblinLair.addNextRoom(storeroom);
        goblinLair.addNextRoom(darkPool);
        mineTunnels.addNextRoom(darkPool);
        mineTunnels.addNextRoom(fungiCavern);
        storeroom.addNextRoom(templeOfDumathoin);
        darkPool.addNextRoom(templeOfDumathoin);
        fungiCavern.addNextRoom(templeOfDumathoin);

        this.addRoom(caveEntrance);
        this.addRoom(goblinLair);
        this.addRoom(mineTunnels);
        this.addRoom(storeroom);
        this.addRoom(darkPool);
        this.addRoom(fungiCavern);
        this.addRoom(templeOfDumathoin);
    }

    private LostMineOfPhandelverDungeon(final LostMineOfPhandelverDungeon dungeon) {
        super(dungeon);
    }

    @Override
    public LostMineOfPhandelverDungeon copy() {
        return new LostMineOfPhandelverDungeon(this);
    }
}
