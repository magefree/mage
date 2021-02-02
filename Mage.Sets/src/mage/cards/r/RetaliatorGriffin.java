
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author North
 */
public final class RetaliatorGriffin extends CardImpl {

    public RetaliatorGriffin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}{G}{W}");
        this.subtype.add(SubType.GRIFFIN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(FlyingAbility.getInstance());
        // Whenever a source an opponent controls deals damage to you, you may put that many +1/+1 counters on Retaliator Griffin.
        this.addAbility(new RetaliatorGriffinTriggeredAbility());
    }

    private RetaliatorGriffin(final RetaliatorGriffin card) {
        super(card);
    }

    @Override
    public RetaliatorGriffin copy() {
        return new RetaliatorGriffin(this);
    }
}

class RetaliatorGriffinTriggeredAbility extends TriggeredAbilityImpl {

    public RetaliatorGriffinTriggeredAbility() {
        super(Zone.BATTLEFIELD, new RetaliatorGriffinEffect(), true);
    }

    public RetaliatorGriffinTriggeredAbility(final RetaliatorGriffinTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RetaliatorGriffinTriggeredAbility copy() {
        return new RetaliatorGriffinTriggeredAbility(this);
    }
    
    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }
    
    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(getControllerId())) {
            UUID sourceControllerId = game.getControllerId(event.getSourceId());
            if (sourceControllerId != null && 
                    game.getOpponents(getControllerId()).contains(sourceControllerId)) {
                getEffects().get(0).setValue("damageAmount", event.getAmount());
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a source an opponent controls deals damage to you, you may put that many +1/+1 counters on {this}.";
    }
}

class RetaliatorGriffinEffect extends OneShotEffect {

    public RetaliatorGriffinEffect() {
        super(Outcome.BoostCreature);
    }

    public RetaliatorGriffinEffect(final RetaliatorGriffinEffect effect) {
        super(effect);
    }

    @Override
    public RetaliatorGriffinEffect copy() {
        return new RetaliatorGriffinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent permanent = game.getPermanent(source.getSourceId());
            Integer amount = (Integer) this.getValue("damageAmount");
            if (permanent != null && amount != null && amount > 0) {
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(amount), true).apply(game, source);
            }            
            return true;
        }
        return false;
    }
}
