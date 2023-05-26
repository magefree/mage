
package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author Styxo/spjspj
 */
public final class GrandMoffTarkin extends CardImpl {

    public GrandMoffTarkin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // At the beggining of each upkeep, destroy target creature that player controls unless that player pays 2 life. If a player pays life this way, draw a card.
        this.addAbility(new GrandMoffTarkinTriggeredAbility(new GrandMoffTarkinEffect(), false));
    }

    private GrandMoffTarkin(final GrandMoffTarkin card) {
        super(card);
    }

    @Override
    public GrandMoffTarkin copy() {
        return new GrandMoffTarkin(this);
    }
}

class GrandMoffTarkinTriggeredAbility extends TriggeredAbilityImpl {

    public GrandMoffTarkinTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        setTriggerPhrase("At the beginning of each opponent's upkeep, ");
    }

    public GrandMoffTarkinTriggeredAbility(final GrandMoffTarkinTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getOpponents(controllerId).contains(event.getPlayerId())) {
            Player opponent = game.getPlayer(event.getPlayerId());
            if (opponent != null) {
                this.getTargets().clear();
                FilterCreaturePermanent filter = new FilterCreaturePermanent("target creature that player controls");
                filter.add(new ControllerIdPredicate(event.getPlayerId()));
                TargetPermanent target = new TargetPermanent(filter);
                this.addTarget(target);
                return true;
            }
        }
        return false;
    }

    @Override
    public GrandMoffTarkinTriggeredAbility copy() {
        return new GrandMoffTarkinTriggeredAbility(this);
    }
}

class GrandMoffTarkinEffect extends OneShotEffect {

    public GrandMoffTarkinEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "destroy target creature that that player controls unless that player pays 2 life. If a player pays life this way, draw a card";
    }

    public GrandMoffTarkinEffect(final GrandMoffTarkinEffect effect) {
        super(effect);
    }

    @Override
    public GrandMoffTarkinEffect copy() {
        return new GrandMoffTarkinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetCreature = game.getPermanent(targetPointer.getFirst(game, source));
        if (targetCreature == null) {
            return false;
        }

        Player player = game.getPlayer(targetCreature.getControllerId());
        if (player == null) {
            return false;
        }

        if (player.getLife() > 2 && player.chooseUse(Outcome.Neutral, "Pay 2 life? If you don't, " + targetCreature.getName() + " will be destroyed", source, game)) {
            player.loseLife(2, game, source, false);
            game.informPlayers(player.getLogName() + " pays 2 life to prevent " + targetCreature.getName() + " being destroyed");
            Player sourceController = game.getPlayer(source.getControllerId());
            if (sourceController != null) {
                sourceController.drawCards(1, source, game);
            }

            return true;
        }

        targetCreature.destroy(source, game, false);
        return true;
    }
}
