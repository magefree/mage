package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalRestrictionEffect;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.combat.CantAttackSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.PermanentToken;
import mage.game.permanent.token.TreasureToken;
import mage.watchers.Watcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GadrakTheCrownScourge extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            StaticFilters.FILTER_PERMANENT_ARTIFACT, ComparisonType.FEWER_THAN, 4
    );

    public GadrakTheCrownScourge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Gadrak, the Crown-Scourge can't attack unless you control four or more artifacts.
        this.addAbility(new SimpleStaticAbility(new ConditionalRestrictionEffect(
                new CantAttackSourceEffect(Duration.WhileOnBattlefield), condition,
                "{this} can't attack unless you control four or more artifacts"
        )));

        // At the beginning of your end step, create a Treasure token for each nontoken creature that died this turn.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new CreateTokenEffect(
                new TreasureToken(), GadrakTheCrownScourgeValue.instance
        ), TargetController.YOU, false), new GadrakTheCrownScourgeWatcher());
    }

    private GadrakTheCrownScourge(final GadrakTheCrownScourge card) {
        super(card);
    }

    @Override
    public GadrakTheCrownScourge copy() {
        return new GadrakTheCrownScourge(this);
    }
}

enum GadrakTheCrownScourgeValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        GadrakTheCrownScourgeWatcher watcher = game.getState().getWatcher(GadrakTheCrownScourgeWatcher.class);
        return watcher != null ? watcher.getDiedThisTurn() : 0;
    }

    @Override
    public GadrakTheCrownScourgeValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "nontoken creature that died this turn";
    }

    @Override
    public String toString() {
        return "1";
    }
}

class GadrakTheCrownScourgeWatcher extends Watcher {

    private int diedThisTurn = 0;

    GadrakTheCrownScourgeWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ZONE_CHANGE) {
            return;
        }
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.isDiesEvent()
                && zEvent.getTarget() != null
                && zEvent.getTarget().isCreature(game)
                && !(zEvent.getTarget() instanceof PermanentToken)) {
            diedThisTurn++;
        }
    }

    @Override
    public void reset() {
        super.reset();
        diedThisTurn = 0;
    }

    int getDiedThisTurn() {
        return diedThisTurn;
    }
}
