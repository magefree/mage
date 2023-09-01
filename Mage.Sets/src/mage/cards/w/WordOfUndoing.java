
package mage.cards.w;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author tcontis
 */
public final class WordOfUndoing extends CardImpl {

    public WordOfUndoing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}");

        //Return target creature and all white Auras you own attached to it to their owners' hands.
        this.getSpellAbility().addEffect(new WordOfUndoingReturnToHandEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

    }

    private WordOfUndoing(final WordOfUndoing card) {
        super(card);
    }

    @Override
    public WordOfUndoing copy() {
        return new WordOfUndoing(this);
    }
}

class WordOfUndoingReturnToHandEffect extends OneShotEffect {

    public WordOfUndoingReturnToHandEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "Return target creature and all white Auras you own attached to it to their owners' hands.";
    }

    private WordOfUndoingReturnToHandEffect(final WordOfUndoingReturnToHandEffect effect) {
        super(effect);
    }

    @Override
    public WordOfUndoingReturnToHandEffect copy() {
        return new WordOfUndoingReturnToHandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Set<Card> attachments = new LinkedHashSet<>();
        Player player = game.getPlayer(source.getControllerId());
        Permanent target = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (target != null && player != null) {
            for (UUID attachmentId : target.getAttachments()) {
                Permanent attachment = game.getPermanent(attachmentId);
                if (attachment != null && attachment.isControlledBy(source.getControllerId())
                        && attachment.hasSubtype(SubType.AURA, game) && attachment.getColor(game).isWhite()) {
                    attachments.add(attachment);
                }
            }
            attachments.add(target);
            player.moveCards(attachments, Zone.HAND, source, game);
            return true;
        }
        return false;
    }
}

