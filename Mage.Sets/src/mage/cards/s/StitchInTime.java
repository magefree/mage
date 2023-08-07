
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.turn.TurnMod;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class StitchInTime extends CardImpl {

    public StitchInTime(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{U}{R}");


        // Flip a coin. If you win the flip, take an extra turn after this one.
        this.getSpellAbility().addEffect(new StitchInTimeEffect());

    }

    private StitchInTime(final StitchInTime card) {
        super(card);
    }

    @Override
    public StitchInTime copy() {
        return new StitchInTime(this);
    }
}

class StitchInTimeEffect extends OneShotEffect {

    public StitchInTimeEffect() {
        super(Outcome.DrawCard);
        staticText = "Flip a coin. If you win the flip, take an extra turn after this one";
    }

    public StitchInTimeEffect(final StitchInTimeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            if (player.flipCoin(source, game, true)) {
                game.getState().getTurnMods().add(new TurnMod(player.getId()).withExtraTurn());
                return true;
            }
        }
        return false;
    }

    @Override
    public StitchInTimeEffect copy() {
        return new StitchInTimeEffect(this);
    }

}
