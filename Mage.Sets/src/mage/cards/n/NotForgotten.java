
package mage.cards.n;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.token.SpiritWhiteToken;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class NotForgotten extends CardImpl {

    public NotForgotten(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{W}");

        // Put target card from a graveyard on the top or bottom of its owner's library. 
        // Create a 1/1 white Spirit creature token with flying.
        this.getSpellAbility().addEffect(new NotForgottenEffect());
        this.getSpellAbility().addTarget(new TargetCardInGraveyard());
    }

    private NotForgotten(final NotForgotten card) {
        super(card);
    }

    @Override
    public NotForgotten copy() {
        return new NotForgotten(this);
    }
}

class NotForgottenEffect extends OneShotEffect {
    
    public NotForgottenEffect() {
        super(Outcome.Benefit);
        this.staticText = "Put target card from a graveyard on the top or bottom of its owner's library. Create a 1/1 white Spirit creature token with flying.";
    }
    
    private NotForgottenEffect(final NotForgottenEffect effect) {
        super(effect);
    }
    
    @Override
    public NotForgottenEffect copy() {
        return new NotForgottenEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card targetCard = game.getCard(source.getFirstTarget());
        
        if (controller != null && targetCard != null) {
            boolean onTop = controller.chooseUse(outcome, "Put " + targetCard.getName() + " on top of it's owners library (otherwise on bottom)?", source, game);
            boolean moved = controller.moveCardToLibraryWithInfo(targetCard, source, game, Zone.GRAVEYARD, onTop, true);
            if (moved) {
                Token token = new SpiritWhiteToken();
                token.putOntoBattlefield(1, game, source, source.getControllerId(), false, false);
                return true;
            }
        }
        return false;
    }
}
