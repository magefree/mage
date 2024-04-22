package mage.cards.a;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.TheRingTemptsYouChooseAnotherTriggeredAbility;
import mage.abilities.effects.common.counter.AddCounterChoiceSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
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
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AragornCompanyLeader extends CardImpl {

    public AragornCompanyLeader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.RANGER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever the Ring tempts you, if you chose a creature other than Aragorn, Company Leader as your Ring-bearer, put your choice of a counter from among first strike, vigilance, deathtouch, and lifelink on Aragorn.
        this.addAbility(new TheRingTemptsYouChooseAnotherTriggeredAbility(new AddCounterChoiceSourceEffect(
                CounterType.FIRST_STRIKE, CounterType.VIGILANCE, CounterType.DEATHTOUCH, CounterType.LIFELINK
        ).setText("put your choice of a counter from among first strike, vigilance, deathtouch, and lifelink on {this}")));

        // Whenever you put one or more counters on Aragorn, put one of each of those kinds of counters on up to one other target creature.
        this.addAbility(new AragornCompanyLeaderTriggeredAbility());
    }

    private AragornCompanyLeader(final AragornCompanyLeader card) {
        super(card);
    }

    @Override
    public AragornCompanyLeader copy() {
        return new AragornCompanyLeader(this);
    }
}

class AragornCompanyLeaderTriggeredAbility extends TriggeredAbilityImpl {

    AragornCompanyLeaderTriggeredAbility() {
        super(Zone.BATTLEFIELD, null);
        this.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_ANOTHER_TARGET_CREATURE));
    }

    private AragornCompanyLeaderTriggeredAbility(final AragornCompanyLeaderTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AragornCompanyLeaderTriggeredAbility copy() {
        return new AragornCompanyLeaderTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTERS_ADDED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!isControlledBy(event.getPlayerId()) || !event.getTargetId().equals(getSourceId())) {
            return false;
        }
        CounterType counterType = CounterType.findByName(event.getData());
        if (counterType == null) {
            return false;
        }
        this.getEffects().clear();
        this.addEffect(new AddCountersTargetEffect(counterType.createInstance()));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever you put one or more counters on {this}, " +
                "put one of each of those kinds of counters on up to one other target creature.";
    }
}
