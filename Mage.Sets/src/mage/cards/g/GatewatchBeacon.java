package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.Effects;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class GatewatchBeacon extends CardImpl {

    public GatewatchBeacon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{W}");

        // Gatewatch Beacon enters the battlefield with three loyalty counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(
                CounterType.LOYALTY.createInstance(3)
        ), "with three loyalty counters on it"));

        // {T}: Add {W}.
        this.addAbility(new WhiteManaAbility());

        // Whenever a planeswalker enters the battlefield under your control, if Gatewatch Beacon has loyalty
        // counters on it, you may move a loyalty counter from Gatewatch Beacon onto that planeswalker.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldControlledTriggeredAbility(
                        Zone.BATTLEFIELD,
                        new GatewatchBeaconMoveCounterEffect(),
                        StaticFilters.FILTER_PERMANENT_PLANESWALKER,
                        true
                ), GatewatchBeaconCondition.instance, "Whenever a planeswalker enters the battlefield under your control, if {this} has " +
                "loyalty counters on it, you may move a loyalty counter from {this} onto that planeswalker"
        ));
    }

    private GatewatchBeacon(final GatewatchBeacon card) {
        super(card);
    }

    @Override
    public GatewatchBeacon copy() {
        return new GatewatchBeacon(this);
    }
}

enum GatewatchBeaconCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            return false;
        }
        Effects effects = source.getEffects();
        if (effects.isEmpty()) {
            return false;
        }

        return permanent.getCounters(game).getCount(CounterType.LOYALTY) > 0;
    }
}

class GatewatchBeaconMoveCounterEffect extends OneShotEffect {

    public GatewatchBeaconMoveCounterEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may move a loyalty counter from {this} onto that planeswalker";
    }

    private GatewatchBeaconMoveCounterEffect(final GatewatchBeaconMoveCounterEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        if (sourcePermanent == null || controller == null) {
            return false;
        }
        Object enteringObject = this.getValue("permanentEnteringBattlefield");
        if (!(enteringObject instanceof Permanent)) {
            return false;
        }
        int numberOfCounters = sourcePermanent.getCounters(game).getCount(CounterType.LOYALTY);
        if (numberOfCounters == 0) {
            return false;
        }
        Permanent enteringPlaneswalker = (Permanent) enteringObject;
        sourcePermanent.removeCounters(CounterType.LOYALTY.getName(), 1, source, game);
        if (!game.isSimulation()) { // planeswalker incorrectly gets two loyalty counters without this check
            enteringPlaneswalker.addCounters(CounterType.LOYALTY.createInstance(), source, game);
        }
        return true;
    }

    @Override
    public GatewatchBeaconMoveCounterEffect copy() {
        return new GatewatchBeaconMoveCounterEffect(this);
    }
}
