
package mage.cards.h;

import java.util.UUID;
import mage.abilities.effects.common.ReplaceOpponentCardsInHandWithSelectedEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetOpponent;

/**
 *
 * @author Eirkei
 */
public final class HeadGames extends CardImpl {

    public HeadGames(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{B}{B}");

        // Target opponent puts the cards from their hand on top of their library. Search that player's library for that many cards. The player puts those cards into their hand, then shuffles their library.
        this.getSpellAbility().addEffect(new ReplaceOpponentCardsInHandWithSelectedEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private HeadGames(final HeadGames card) {
        super(card);
    }

    @Override
    public HeadGames copy() {
        return new HeadGames(this);
    }
}
