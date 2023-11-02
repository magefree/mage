
package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author North
 */
public final class InvincibleHymn extends CardImpl {

    public InvincibleHymn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{6}{W}{W}");


        // Count the number of cards in your library. Your life total becomes that number.
        this.getSpellAbility().addEffect(new InvincibleHymnEffect());
    }

    private InvincibleHymn(final InvincibleHymn card) {
        super(card);
    }

    @Override
    public InvincibleHymn copy() {
        return new InvincibleHymn(this);
    }
}

class InvincibleHymnEffect extends OneShotEffect {

    public InvincibleHymnEffect() {
        super(Outcome.Neutral);
        this.staticText = "Count the number of cards in your library. Your life total becomes that number";
    }

    private InvincibleHymnEffect(final InvincibleHymnEffect effect) {
        super(effect);
    }

    @Override
    public InvincibleHymnEffect copy() {
        return new InvincibleHymnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            int newValue = player.getLibrary().size();
            int oldValue = player.getLife();

            if (newValue - oldValue > 0) {
                player.gainLife(newValue - oldValue, game, source);
            }
            if (oldValue - newValue > 0) {
                player.loseLife(oldValue - newValue, game, source, false);
            }
            return true;
        }
        return false;
    }
}
