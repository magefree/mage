package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WildbornPreserver extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("another non-Human creature");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(Predicates.not(new SubtypePredicate(SubType.HUMAN)));
    }

    public WildbornPreserver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ARCHER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Whenever another non-Human creature enters the battlefield under your control, you may pay {X}. When you do, put X +1/+1 counters on Wildborn Preserver.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new WildbornPreserverCreateReflexiveTriggerEffect(), filter
        ));
    }

    private WildbornPreserver(final WildbornPreserver card) {
        super(card);
    }

    @Override
    public WildbornPreserver copy() {
        return new WildbornPreserver(this);
    }
}

class WildbornPreserverCreateReflexiveTriggerEffect extends OneShotEffect {

    WildbornPreserverCreateReflexiveTriggerEffect() {
        super(Outcome.Benefit);
    }

    private WildbornPreserverCreateReflexiveTriggerEffect(final WildbornPreserverCreateReflexiveTriggerEffect effect) {
        super(effect);
        staticText = "you may pay {X}. When you do, put X +1/+1 counters on {this}";
    }

    @Override
    public WildbornPreserverCreateReflexiveTriggerEffect copy() {
        return new WildbornPreserverCreateReflexiveTriggerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        ManaCosts cost = new ManaCostsImpl("{X}");
        if (player == null) {
            return false;
        }
        if (!player.chooseUse(outcome, "Pay " + cost.getText() + "?", source, game)) {
            return false;
        }
        int costX = player.announceXMana(0, Integer.MAX_VALUE, "Announce the value for {X}", game, source);
        cost.add(new GenericManaCost(costX));
        if (!cost.pay(source, game, source.getSourceId(), source.getControllerId(), false, null)) {
            return false;
        }
        game.addDelayedTriggeredAbility(new WildbornPreserverReflexiveTriggeredAbility(costX), source);
        game.fireEvent(GameEvent.getEvent(GameEvent.EventType.OPTION_USED, source.getOriginalId(), source.getSourceId(), source.getControllerId(), 0));
        return true;
    }
}

class WildbornPreserverReflexiveTriggeredAbility extends DelayedTriggeredAbility {

    WildbornPreserverReflexiveTriggeredAbility(int counters) {
        super(new AddCountersSourceEffect(CounterType.P1P1.createInstance(counters)), Duration.OneUse, true);
    }

    private WildbornPreserverReflexiveTriggeredAbility(final WildbornPreserverReflexiveTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public WildbornPreserverReflexiveTriggeredAbility copy() {
        return new WildbornPreserverReflexiveTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.OPTION_USED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.getControllerId())
                && event.getSourceId().equals(this.getSourceId());
    }

    @Override
    public String getRule() {
        return "When you do, put X +1/+1 counters on {this}.";
    }
}
