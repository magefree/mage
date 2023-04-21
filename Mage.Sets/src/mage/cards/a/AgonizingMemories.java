
package mage.cards.a;

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
import mage.target.TargetPlayer;

/**
 *
 * @author Quercitron
 */
public final class AgonizingMemories extends CardImpl {

    public AgonizingMemories(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B}{B}");


        // Look at target player's hand and choose two cards from it. Put them on top of that player's library in any order.
        this.getSpellAbility().addEffect(new AgonizingMemoriesEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private AgonizingMemories(final AgonizingMemories card) {
        super(card);
    }

    @Override
    public AgonizingMemories copy() {
        return new AgonizingMemories(this);
    }
}

class AgonizingMemoriesEffect extends OneShotEffect {

    public AgonizingMemoriesEffect() {
        super(Outcome.Discard);
        this.staticText = "Look at target player's hand and choose two cards from it. Put them on top of that player's library in any order.";
    }

    public AgonizingMemoriesEffect(final AgonizingMemoriesEffect effect) {
        super(effect);
    }

    @Override
    public AgonizingMemoriesEffect copy() {
        return new AgonizingMemoriesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        Player you = game.getPlayer(source.getControllerId());
        if (targetPlayer != null && you != null) {
            chooseCardInHandAndPutOnTopOfLibrary(game, source, you, targetPlayer);
            chooseCardInHandAndPutOnTopOfLibrary(game, source, you, targetPlayer);
            return true;
        }
        return false;
    }
    
    private void chooseCardInHandAndPutOnTopOfLibrary(Game game, Ability source, Player you, Player targetPlayer) {
        if (!targetPlayer.getHand().isEmpty()) {
            TargetCard target = new TargetCard(Zone.HAND, new FilterCard("card to put on the top of library (last chosen will be on top)"));
            if (you.choose(Outcome.Benefit, targetPlayer.getHand(), target, source, game)) {
                Card card = targetPlayer.getHand().get(target.getFirstTarget(), game);
                if (card != null) {
                    targetPlayer.moveCardToLibraryWithInfo(card, source, game, Zone.HAND, true, false);
                }
            }
        }
    }
}
