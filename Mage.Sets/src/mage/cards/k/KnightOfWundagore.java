package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import java.util.UUID;

/**
 * @author muz
 */
public final class KnightOfWundagore extends CardImpl {

    public KnightOfWundagore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.KNIGHT);
        this.subtype.add(SubType.VILLAIN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever you put a +1/+1 counter on another creature, put a +1/+1 counter on this creature. This ability triggers only once each turn.
        Ability ability = new KnightOfWundagoreTriggeredAbility().setTriggersLimitEachTurn(1);
        this.addAbility(ability);
    }

    private KnightOfWundagore(final KnightOfWundagore card) {
        super(card);
    }

    @Override
    public KnightOfWundagore copy() {
        return new KnightOfWundagore(this);
    }
}

class KnightOfWundagoreTriggeredAbility extends TriggeredAbilityImpl {

    KnightOfWundagoreTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()));
        setTriggerPhrase("Whenever you put a +1/+1 counter on another creature, ");
    }

    private KnightOfWundagoreTriggeredAbility(final KnightOfWundagoreTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTER_ADDED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getPlayerId().equals(getControllerId()) || !event.getData().equals(CounterType.P1P1.getName())) {
            return false;
        }
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
        return permanent != null && StaticFilters.FILTER_ANOTHER_CREATURE.match(permanent, getControllerId(), this, game);
    }

    @Override
    public KnightOfWundagoreTriggeredAbility copy() {
        return new KnightOfWundagoreTriggeredAbility(this);
    }
}
