

package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author Loki
 */
public final class IdentityCrisis extends CardImpl {

    public IdentityCrisis (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{W}{W}{B}{B}");

        
        this.getSpellAbility().addEffect(new IdentityCrisisEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    public IdentityCrisis (final IdentityCrisis card) {
        super(card);
    }

    @Override
    public IdentityCrisis copy() {
        return new IdentityCrisis(this);
    }

}

class IdentityCrisisEffect extends OneShotEffect {
    IdentityCrisisEffect() {
        super(Outcome.Exile);
        staticText = "Exile all cards from target player's hand and graveyard";
    }

    IdentityCrisisEffect(final IdentityCrisisEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player != null) {
            for (UUID cid : player.getHand().copy()) {
                Card c = game.getCard(cid);
                if (c != null) {
                    c.moveToExile(null, null, source.getSourceId(), game);
                }
            }
            for (UUID cid : player.getGraveyard().copy()) {
                Card c = game.getCard(cid);
                if (c != null) {
                    c.moveToExile(null, null, source.getSourceId(), game);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public IdentityCrisisEffect copy() {
        return new IdentityCrisisEffect(this);
    }

 }