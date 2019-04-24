
package mage.cards.c;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetNonlandPermanent;

/**
 *
 * @author Plopman
 */
public final class ChainOfVapor extends CardImpl {

    public ChainOfVapor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}");

        // Return target nonland permanent to its owner's hand. Then that permanent's controller may sacrifice a land. If the player does, he or she may copy this spell and may choose a new target for that copy.
        this.getSpellAbility().addEffect(new ChainOfVaporEffect());
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());
    }

    public ChainOfVapor(final ChainOfVapor card) {
        super(card);
    }

    @Override
    public ChainOfVapor copy() {
        return new ChainOfVapor(this);
    }
}

class ChainOfVaporEffect extends OneShotEffect {

    public ChainOfVaporEffect() {
        super(Outcome.ReturnToHand);
    }

    public ChainOfVaporEffect(final ChainOfVaporEffect effect) {
        super(effect);
    }

    @Override
    public ChainOfVaporEffect copy() {
        return new ChainOfVaporEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller == null || sourceObject == null) {
            return false;
        }
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            controller.moveCards(permanent, Zone.HAND, source, game);
            Player player = game.getPlayer(permanent.getControllerId());
            TargetControlledPermanent target = new TargetControlledPermanent(0, 1, new FilterControlledLandPermanent("a land to sacrifice (to be able to copy " + sourceObject.getName() + ')'), true);
            if (player.chooseTarget(Outcome.Sacrifice, target, source, game)) {
                Permanent land = game.getPermanent(target.getFirstTarget());
                if (land != null && land.sacrifice(source.getSourceId(), game)) {
                    if (player.chooseUse(outcome, "Copy the spell?", source, game)) {
                        Spell spell = game.getStack().getSpell(source.getSourceId());
                        if (spell != null) {
                            StackObject newStackObject = spell.createCopyOnStack(game, source, player.getId(), true);
                            if (newStackObject instanceof Spell) {
                                String activateMessage = ((Spell) newStackObject).getActivatedMessage(game);
                                if (activateMessage.startsWith(" casts ")) {
                                    activateMessage = activateMessage.substring(6);
                                }
                                game.informPlayers(player.getLogName() + ' ' + activateMessage);
                            }
                        }
                    }
                }
            }
            return true;
        }

        return false;
    }

    @Override
    public String getText(Mode mode) {
        return "Return target nonland permanent to its owner's hand. Then that permanent's controller may sacrifice a land. If the player does, he or she may copy this spell and may choose a new target for that copy";
    }

}
