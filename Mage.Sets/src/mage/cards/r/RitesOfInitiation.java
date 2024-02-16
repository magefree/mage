
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author emerald000
 */
public final class RitesOfInitiation extends CardImpl {

    public RitesOfInitiation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{R}");

        // Discard any number of cards at random. Creatures you control get +1/+0 until end of turn for each card discarded this way.
        this.getSpellAbility().addEffect(new RitesOfInitiationEffect());
    }

    private RitesOfInitiation(final RitesOfInitiation card) {
        super(card);
    }

    @Override
    public RitesOfInitiation copy() {
        return new RitesOfInitiation(this);
    }
}

class RitesOfInitiationEffect extends OneShotEffect {
    
    RitesOfInitiationEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "Discard any number of cards at random. Creatures you control get +1/+0 until end of turn for each card discarded this way";
    }
    
    private RitesOfInitiationEffect(final RitesOfInitiationEffect effect) {
        super(effect);
    }
    
    @Override
    public RitesOfInitiationEffect copy() {
        return new RitesOfInitiationEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {            
            int numToDiscard = player.getAmount(0, player.getHand().size(), "Discard how many cards at random?", game);
            player.discard(numToDiscard, true, false, source, game);
            game.addEffect(new BoostControlledEffect(numToDiscard, 0, Duration.EndOfTurn), source);
            return true;
        }
        return false;
    }
}
