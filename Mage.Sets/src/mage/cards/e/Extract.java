package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author cbt33, jeffwadsworth (Supreme Inquisitor)
 */
public final class Extract extends CardImpl {

    public Extract(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{U}");

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
        if (player == null || targetPlayer == null) {
            return false;
        }
        TargetCardInLibrary target = new TargetCardInLibrary();
        player.searchLibrary(target, source, game, targetPlayer.getId());
        Card card = targetPlayer.getLibrary().getCard(target.getFirstTarget(), game);
        if (card != null) {
            player.moveCards(card, Zone.EXILED, source, game);
        }
        targetPlayer.shuffleLibrary(source, game);
        return true;
    }
}
