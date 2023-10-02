
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

/**
 *
 * @author Styxo
 */
public final class SyndicateEnforcerSWS extends CardImpl {

    public SyndicateEnforcerSWS(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.subtype.add(SubType.GAND);
        this.subtype.add(SubType.HUNTER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When {this} enters the battlefield, put a bounty counter on target creature an opponent controls 
        Ability ability = new EntersBattlefieldTriggeredAbility(new AddCountersTargetEffect(CounterType.BOUNTY.createInstance()));
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);

        // Whenever a bounty counter is placed on a creature an opponents controls, that creature can't block this turn.
        this.addAbility(new SyndicateEnforcerTriggeredAbility());
    }

    private SyndicateEnforcerSWS(final SyndicateEnforcerSWS card) {
        super(card);
    }

    @Override
    public SyndicateEnforcerSWS copy() {
        return new SyndicateEnforcerSWS(this);
    }
}

class SyndicateEnforcerTriggeredAbility extends TriggeredAbilityImpl {

    private static final Effect effect = new CantBlockTargetEffect(Duration.EndOfTurn);

    public SyndicateEnforcerTriggeredAbility() {
        super(Zone.BATTLEFIELD, effect, false);
    }

    private SyndicateEnforcerTriggeredAbility(final SyndicateEnforcerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SyndicateEnforcerTriggeredAbility copy() {
        return new SyndicateEnforcerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTERS_ADDED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getData().equals(CounterType.BOUNTY.getName())) {
            Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
            if (permanent != null) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a bounty counter is placed on a creature an opponents controls, that creature can't block this turn.";
    }
}
