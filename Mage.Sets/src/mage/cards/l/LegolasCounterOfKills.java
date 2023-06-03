package mage.cards.l;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LegolasCounterOfKills extends CardImpl {

    public LegolasCounterOfKills(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ARCHER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Whenever you scry, if Legolas, Counter of Kills is tapped, you may untap it. Do this only once each turn.
        this.addAbility(new LegolasCounterOfKillsTriggeredAbility());

        // Whenever a creature an opponent controls dies, put a +1/+1 counter on Legolas.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false,
                StaticFilters.FILTER_OPPONENTS_PERMANENT_A_CREATURE
        ));
    }

    private LegolasCounterOfKills(final LegolasCounterOfKills card) {
        super(card);
    }

    @Override
    public LegolasCounterOfKills copy() {
        return new LegolasCounterOfKills(this);
    }
}

class LegolasCounterOfKillsTriggeredAbility extends TriggeredAbilityImpl {

    LegolasCounterOfKillsTriggeredAbility() {
        super(Zone.BATTLEFIELD, new UntapSourceEffect(), true);
        this.setDoOnlyOnce(true);
    }

    private LegolasCounterOfKillsTriggeredAbility(final LegolasCounterOfKillsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public LegolasCounterOfKillsTriggeredAbility copy() {
        return new LegolasCounterOfKillsTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SCRIED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return isControlledBy(event.getPlayerId());
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        return Optional
                .ofNullable(this.getSourcePermanentIfItStillExists(game))
                .filter(Objects::nonNull)
                .map(Permanent::isTapped)
                .orElse(false);
    }

    @Override
    public String getRule() {
        return "Whenever you scry, if Legolas, Counter of Kills is tapped, you may untap it. Do this only once each turn.";
    }
}
