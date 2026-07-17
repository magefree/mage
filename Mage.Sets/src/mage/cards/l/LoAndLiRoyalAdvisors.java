package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LoAndLiRoyalAdvisors extends CardImpl {

    public LoAndLiRoyalAdvisors(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever an opponent discards a card or mills one or more cards, put a +1/+1 counter on each Advisor you control.
        this.addAbility(new LoAndLiRoyalAdvisorsTriggeredAbility());

        // {2}{U/B}: Target player mills four cards.
        Ability ability = new SimpleActivatedAbility(new MillCardsTargetEffect(4), new ManaCostsImpl<>("{2}{U/B}"));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private LoAndLiRoyalAdvisors(final LoAndLiRoyalAdvisors card) {
        super(card);
    }

    @Override
    public LoAndLiRoyalAdvisors copy() {
        return new LoAndLiRoyalAdvisors(this);
    }
}

class LoAndLiRoyalAdvisorsTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.ADVISOR);

    LoAndLiRoyalAdvisorsTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersAllEffect(CounterType.P1P1.createInstance(), filter));
        setTriggerPhrase("Whenever an opponent discards a card or mills one or more cards, ");
    }

    private LoAndLiRoyalAdvisorsTriggeredAbility(final LoAndLiRoyalAdvisorsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public LoAndLiRoyalAdvisorsTriggeredAbility copy() {
        return new LoAndLiRoyalAdvisorsTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DISCARDED_CARD
                || event.getType() == GameEvent.EventType.MILLED_CARDS_BATCH_FOR_ONE_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game.getOpponents(getControllerId()).contains(event.getPlayerId());
    }
}
