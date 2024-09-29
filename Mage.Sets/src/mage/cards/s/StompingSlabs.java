
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author emerald000
 */
public final class StompingSlabs extends CardImpl {

    public StompingSlabs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{R}");

        // Reveal the top seven cards of your library, then put those cards on the bottom of your library in any order. If a card named Stomping Slabs was revealed this way, Stomping Slabs deals 7 damage to any target.
        this.getSpellAbility().addEffect(new StompingSlabsEffect());
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private StompingSlabs(final StompingSlabs card) {
        super(card);
    }

    @Override
    public StompingSlabs copy() {
        return new StompingSlabs(this);
    }
}

class StompingSlabsEffect extends OneShotEffect {
    
    StompingSlabsEffect() {
        super(Outcome.Damage);
        this.staticText = "Reveal the top seven cards of your library, then put those cards on the bottom of your library in any order. If a card named Stomping Slabs was revealed this way, {this} deals 7 damage to any target";
    }
    
    private StompingSlabsEffect(final StompingSlabsEffect effect) {
        super(effect);
    }
    
    @Override
    public StompingSlabsEffect copy() {
        return new StompingSlabsEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, 7));
            if (!cards.isEmpty()) {
                controller.revealCards("Stomping Slabs", cards, game);
                boolean stompingSlabsFound = false;
                for (UUID cardId : cards) {
                    Card card = game.getCard(cardId);
                    if (card != null && card.getName().equals("Stomping Slabs")) {
                        stompingSlabsFound = true;
                        break;
                    }
                }
                controller.putCardsOnBottomOfLibrary(cards, game, source, true);
                if (stompingSlabsFound) {
                    Effect effect = new DamageTargetEffect(7);
                    effect.setTargetPointer(new FixedTarget(this.getTargetPointer().getFirst(game, source)));
                    effect.apply(game, source);
                }
            }
            return true;
        }
        return false;
    }
}
