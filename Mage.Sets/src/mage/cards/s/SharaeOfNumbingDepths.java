package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.TapTargetEffect;
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
import mage.game.permanent.Permanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author Susucr
 */
public final class SharaeOfNumbingDepths extends CardImpl {

    public SharaeOfNumbingDepths(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{U}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When Sharae of Numbing Depths enters the battlefield, tap target creature an opponent controls and put a stun counter on it.
        Ability ability = new EntersBattlefieldTriggeredAbility(new TapTargetEffect());
        ability.addEffect(new AddCountersTargetEffect(CounterType.STUN.createInstance()).setText("and put a stun counter on it"));
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);

        // Whenever you tap one or more untapped creatures your opponents control, draw a card. This ability triggers only once each turn.
        this.addAbility(new SharaeOfNumbingDepthsTriggeredAbility());
    }

    private SharaeOfNumbingDepths(final SharaeOfNumbingDepths card) {
        super(card);
    }

    @Override
    public SharaeOfNumbingDepths copy() {
        return new SharaeOfNumbingDepths(this);
    }
}

class SharaeOfNumbingDepthsTriggeredAbility extends TriggeredAbilityImpl {

    SharaeOfNumbingDepthsTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1));
        this.setTriggersOnceEachTurn(true);
        setTriggerPhrase("Whenever you tap one or more untapped creatures your opponents control, ");
    }

    private SharaeOfNumbingDepthsTriggeredAbility(final SharaeOfNumbingDepthsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SharaeOfNumbingDepthsTriggeredAbility copy() {
        return new SharaeOfNumbingDepthsTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TAPPED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        return permanent != null
                && StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE.match(permanent, game)
                && isControlledBy(event.getPlayerId());
    }
}