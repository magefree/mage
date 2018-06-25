
package mage.cards.b;

import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.CardsInHandCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author Plopman
 */
public final class BrinkOfMadness extends CardImpl {

    public BrinkOfMadness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{B}{B}");


        // At the beginning of your upkeep, if you have no cards in hand, sacrifice Brink of Madness and target opponent discards their hand.
        TriggeredAbility ability  = new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new SacrificeSourceEffect(), TargetController.YOU, false);
        ability.addEffect(new BrinkOfMadnessEffect());
        ability.addTarget(new TargetOpponent());
        CardsInHandCondition contition = new CardsInHandCondition(ComparisonType.EQUAL_TO, 0);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, contition, "At the beginning of your upkeep, if you have no cards in hand, sacrifice {this} and target opponent discards their hand."));
        
    }

    public BrinkOfMadness(final BrinkOfMadness card) {
        super(card);
    }

    @Override
    public BrinkOfMadness copy() {
        return new BrinkOfMadness(this);
    }
    
    static class BrinkOfMadnessEffect extends OneShotEffect {

    public BrinkOfMadnessEffect() {
        super(Outcome.Benefit);
        this.staticText = "Target player discards their hand";
    }

    public BrinkOfMadnessEffect(final BrinkOfMadnessEffect effect) {
        super(effect);
    }

    @Override
    public BrinkOfMadnessEffect copy() {
        return new BrinkOfMadnessEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player != null) {
            Set<Card> cards = player.getHand().getCards(game);
            for (Card card : cards) {
                player.discard(card, source, game);
            }
            return true;
        }
        return false;
    }
}
}
