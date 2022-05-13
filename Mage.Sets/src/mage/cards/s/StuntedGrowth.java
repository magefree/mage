
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author LevelX2
 */
public final class StuntedGrowth extends CardImpl {

    public StuntedGrowth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{G}{G}");


        // Target player chooses three cards from their hand and puts them on top of their library in any order.
        this.getSpellAbility().addEffect(new StuntedGrowthEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private StuntedGrowth(final StuntedGrowth card) {
        super(card);
    }

    @Override
    public StuntedGrowth copy() {
        return new StuntedGrowth(this);
    }
}

class StuntedGrowthEffect extends OneShotEffect {

    public StuntedGrowthEffect() {
        super(Outcome.Benefit);
        this.staticText = "Target player chooses three cards from their hand and puts them on top of their library in any order";
    }

    public StuntedGrowthEffect(final StuntedGrowthEffect effect) {
        super(effect);
    }

    @Override
    public StuntedGrowthEffect copy() {
        return new StuntedGrowthEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (targetPlayer != null) {
            int possibleNumber = Math.min(3, targetPlayer.getHand().size());
            if (possibleNumber > 0) {
                Target target = new TargetCardInHand(possibleNumber, new FilterCard("cards from your hand"));
                targetPlayer.choose(outcome, target, source, game);
                Cards cards = new CardsImpl();
                for (UUID cardId: target.getTargets()) {
                    Card card = game.getCard(cardId);
                    if (card != null) {
                        cards.add(card);
                    }
                }
                targetPlayer.putCardsOnTopOfLibrary(cards, game, source, true);
            }
            return true;
        }
        return false;
    }
}
