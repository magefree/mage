

package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */


public final class CryptIncursion extends CardImpl {

    public CryptIncursion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{B}");


        // Exile all creature cards from target player's graveyard. You gain 3 life for each card exiled this way.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new CryptIncursionEffect());


    }

    public CryptIncursion(final CryptIncursion card) {
        super(card);
    }

    @Override
    public CryptIncursion copy() {
        return new CryptIncursion(this);
    }

}

class CryptIncursionEffect extends OneShotEffect {

    private static final FilterCreatureCard filter = new FilterCreatureCard();

    public CryptIncursionEffect() {
        super(Outcome.Detriment);
        staticText = "Exile all creature cards from target player's graveyard. You gain 3 life for each card exiled this way";
    }

    public CryptIncursionEffect(final CryptIncursionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if (player != null && targetPlayer != null) {
            int exiledCards = 0;
            for (Card card: targetPlayer.getGraveyard().getCards(game)) {
                if (filter.match(card, game)) {
                    if (card.moveToExile(null, "", source.getSourceId(), game)) {
                        exiledCards++;
                    }
                }
            }
            player.gainLife(exiledCards * 3, game, source);
            return true;
        }
        return false;
    }

    @Override
    public CryptIncursionEffect copy() {
        return new CryptIncursionEffect(this);
    }

}
