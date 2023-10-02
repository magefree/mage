
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

/**
 *
 * @author LoneFox
 */
public final class SqueesRevenge extends CardImpl {

    public SqueesRevenge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{U}{R}");

        // Choose a number. Flip a coin that many times or until you lose a flip, whichever comes first. If you win all the flips, draw two cards for each flip.
        this.getSpellAbility().addEffect(new SqueesRevengeEffect());
    }

    private SqueesRevenge(final SqueesRevenge card) {
        super(card);
    }

    @Override
    public SqueesRevenge copy() {
        return new SqueesRevenge(this);
    }
}

class SqueesRevengeEffect extends OneShotEffect {

    public SqueesRevengeEffect() {
        super(Outcome.DrawCard);
        staticText = "Choose a number. Flip a coin that many times or until you lose a flip, whichever comes first. If you win all the flips, draw two cards for each flip.";
    }

    private SqueesRevengeEffect(final SqueesRevengeEffect effect) {
        super(effect);
    }

    public SqueesRevengeEffect copy() {
        return new SqueesRevengeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if(player != null) {
            int number = player.announceXMana(0, Integer.MAX_VALUE, "Choose how many times to flip a coin", game, source);
            game.informPlayers(player.getLogName() + " chooses " + number + '.');
            for(int i = 0; i < number; i++) {
                if(!player.flipCoin(source, game, true)) {
                    return true;
                }
            }
            player.drawCards(2 * number, source, game);
            return true;
        }
        return false;
    }
}
