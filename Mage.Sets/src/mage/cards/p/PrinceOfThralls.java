
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public final class PrinceOfThralls extends CardImpl {

    public PrinceOfThralls(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}{B}{B}{R}");
        this.subtype.add(SubType.DEMON);

        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Whenever a permanent an opponent controls is put into a graveyard, put that card onto the battlefield under your control unless that opponent pays 3 life.
        this.addAbility(new PrinceOfThrallsTriggeredAbility(new PrinceOfThrallsEffect()));
    }

    private PrinceOfThralls(final PrinceOfThralls card) {
        super(card);
    }

    @Override
    public PrinceOfThralls copy() {
        return new PrinceOfThralls(this);
    }
}

class PrinceOfThrallsTriggeredAbility extends TriggeredAbilityImpl {

    PrinceOfThrallsTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, false);
        setTriggerPhrase("Whenever a permanent an opponent controls is put into a graveyard, ");
    }

    PrinceOfThrallsTriggeredAbility(final PrinceOfThrallsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PrinceOfThrallsTriggeredAbility copy() {
        return new PrinceOfThrallsTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.isDiesEvent()) {
            Permanent permanent = (Permanent) game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD);
            if (game.getOpponents(this.getControllerId()).contains(permanent.getControllerId())) {
                for (Effect effect : getEffects()) {
                    effect.setTargetPointer(new FixedTarget(event.getTargetId(), game.getState().getZoneChangeCounter(event.getTargetId())));
                }
                return true;
            }
        }
        return false;
    }
}

class PrinceOfThrallsEffect extends OneShotEffect {

    public PrinceOfThrallsEffect() {
        super(Outcome.Neutral);
        this.staticText = "put that card onto the battlefield under your control unless that opponent pays 3 life";
    }

    public PrinceOfThrallsEffect(final PrinceOfThrallsEffect effect) {
        super(effect);
    }

    @Override
    public PrinceOfThrallsEffect copy() {
        return new PrinceOfThrallsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = game.getCard(targetPointer.getFirst(game, source));
        Permanent permanent = (Permanent) game.getLastKnownInformation(targetPointer.getFirst(game, source), Zone.BATTLEFIELD);
        if (controller != null && card != null && permanent != null) {
            Player opponent = game.getPlayer(permanent.getControllerId());
            if (opponent != null) {
                PayLifeCost cost = new PayLifeCost(3);
                if (opponent.chooseUse(Outcome.Neutral, cost.getText() + " or " + card.getLogName() + " comes back into the battlefield under opponents control", source, game)) {
                    cost.clearPaid();
                    if (cost.pay(source, game, source, opponent.getId(), true, null)) {
                        return true;
                    }
                }
                controller.moveCards(card, Zone.BATTLEFIELD, source, game);
            }
            return true;
        }
        return false;
    }
}
