
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public final class SearchWarrant extends CardImpl {

    public SearchWarrant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{W}{U}");


        // Target player reveals their hand. You gain life equal to the number of cards in that player's hand.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new SearchWarrantEffect());

    }

    private SearchWarrant(final SearchWarrant card) {
        super(card);
    }

    @Override
    public SearchWarrant copy() {
        return new SearchWarrant(this);
    }
}

class SearchWarrantEffect extends OneShotEffect {

    public SearchWarrantEffect() {
        super(Outcome.Exile);
        this.staticText = "Target player reveals their hand. You gain life equal to the number of cards in that player's hand";
    }

    private SearchWarrantEffect(final SearchWarrantEffect effect) {
        super(effect);
    }

    @Override
    public SearchWarrantEffect copy() {
        return new SearchWarrantEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if (player != null && targetPlayer != null) {
            targetPlayer.revealCards("Search Warrant", targetPlayer.getHand(), game);
            int ctd = targetPlayer.getHand().size();
            player.gainLife(ctd, game, source);
            return true;
        }
        return false;
    }
}