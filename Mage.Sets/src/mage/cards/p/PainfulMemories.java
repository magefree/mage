
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;

/**
 *
 * @author Quercitron
 */
public final class PainfulMemories extends CardImpl {

    public PainfulMemories(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{B}");


        // Look at target opponent's hand and choose a card from it. Put that card on top of that player's library.
        this.getSpellAbility().addEffect(new PainfulMemoriesEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private PainfulMemories(final PainfulMemories card) {
        super(card);
    }

    @Override
    public PainfulMemories copy() {
        return new PainfulMemories(this);
    }
}

class PainfulMemoriesEffect extends OneShotEffect {

    public PainfulMemoriesEffect() {
        super(Outcome.Discard);
        this.staticText = "Look at target opponent's hand and choose a card from it. Put that card on top of that player's library.";
    }
    
    public PainfulMemoriesEffect(final PainfulMemoriesEffect effect) {
        super(effect);
    }

    @Override
    public PainfulMemoriesEffect copy() {
        return new PainfulMemoriesEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        Player you = game.getPlayer(source.getControllerId());
        if (targetPlayer != null && you != null) {
            targetPlayer.revealCards("Painful Memories", targetPlayer.getHand(), game);
            
            if (!targetPlayer.getHand().isEmpty()) {
                TargetCard target = new TargetCard(Zone.HAND, new FilterCard());
                if (you.choose(Outcome.Benefit, targetPlayer.getHand(), target, source, game)) {
                    Card card = targetPlayer.getHand().get(target.getFirstTarget(), game);
                    if (card != null) {
                        return targetPlayer.moveCardToLibraryWithInfo(card, source, game, Zone.HAND, true, true);
                    }
                }
            }
            return true;
        }
        return false;
    }
    
}
