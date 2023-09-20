
package mage.cards.w;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageEverythingEffect;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public final class WinterSky extends CardImpl {

    public WinterSky(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{R}");

        // Flip a coin. If you win the flip, Winter Sky deals 1 damage to each creature and each player. If you lose the flip, each player draws a card.
        this.getSpellAbility().addEffect(new WinterSkyEffect());
    }

    private WinterSky(final WinterSky card) {
        super(card);
    }

    @Override
    public WinterSky copy() {
        return new WinterSky(this);
    }
}

class WinterSkyEffect extends OneShotEffect {

    public WinterSkyEffect() {
        super(Outcome.Damage);
        staticText = "Flip a coin. If you win the flip, {this} deals 1 damage to each creature and each player. If you lose the flip, each player draws a card";
    }

    private WinterSkyEffect(final WinterSkyEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (controller.flipCoin(source, game, true)) {
                new DamageEverythingEffect(1).apply(game, source);
                return true;
            } else {
                new DrawCardAllEffect(1).apply(game, source);
                return true;
                }
            }
        return false;
    }

    @Override
    public WinterSkyEffect copy() {
        return new WinterSkyEffect(this);
    }
}
