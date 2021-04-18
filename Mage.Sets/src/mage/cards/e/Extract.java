
package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author cbt33, jeffwadsworth (Supreme Inquisitor)
 */
public final class Extract extends CardImpl {

    public Extract(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{U}");


        // Search target player's library for a card and exile it. Then that player shuffles their library.
        this.getSpellAbility().addEffect(new ExtractEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
   
    }

    private Extract(final Extract card) {
        super(card);
    }

    @Override
    public Extract copy() {
        return new Extract(this);
    }
}

class ExtractEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard();

    public ExtractEffect() {
        super(Outcome.Exile);
        staticText = "Search target player's library for a card and exile it. Then that player shuffles.";
    }

    public ExtractEffect(final ExtractEffect effect) {
        super(effect);
    }

    @Override
    public ExtractEffect copy() {
        return new ExtractEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        Player player = game.getPlayer(source.getControllerId());
        if (player != null && targetPlayer != null) {
            TargetCardInLibrary target = new TargetCardInLibrary(1, 1, filter);
            if (player.searchLibrary(target, source, game, targetPlayer.getId())) {
                Card card = targetPlayer.getLibrary().remove(target.getFirstTarget(), game);
                if (card != null) {
                    player.moveCardToExileWithInfo(card, null, null, source, game, Zone.LIBRARY, true);
                }
            }
            targetPlayer.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }
}
