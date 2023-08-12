
package mage.cards.l;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author fireshoes
 */
public final class LearnFromThePast extends CardImpl {

    public LearnFromThePast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{U}");

        // Target player shuffles their graveyard into their library
        this.getSpellAbility().addEffect(new LearnFromThePastEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
        
        // Draw a card
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private LearnFromThePast(final LearnFromThePast card) {
        super(card);
    }

    @Override
    public LearnFromThePast copy() {
        return new LearnFromThePast(this);
    }
}

class LearnFromThePastEffect extends OneShotEffect {
    
    LearnFromThePastEffect() {
        super(Outcome.Neutral);
        this.staticText = "Target player shuffles their graveyard into their library";
    }
    
    LearnFromThePastEffect(final LearnFromThePastEffect effect) {
        super(effect);
    }
    
    @Override
    public LearnFromThePastEffect copy() {
        return new LearnFromThePastEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (player != null) {
            for (Card card: player.getGraveyard().getCards(game)) {
                player.moveCardToLibraryWithInfo(card, source, game, Zone.GRAVEYARD, true, true);
            }            
            player.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }
}