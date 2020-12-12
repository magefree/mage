package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.CardsInHandCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class BrinkOfMadness extends CardImpl {

    public BrinkOfMadness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}{B}");

        // At the beginning of your upkeep, if you have no cards in hand, sacrifice Brink of Madness and target opponent discards their hand.
        TriggeredAbility ability = new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new SacrificeSourceEffect(), TargetController.YOU, false);
        ability.addEffect(new BrinkOfMadnessEffect());
        ability.addTarget(new TargetOpponent());
        CardsInHandCondition contition = new CardsInHandCondition(ComparisonType.EQUAL_TO, 0);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, contition, "At the beginning of your upkeep, if you have no cards in hand, sacrifice {this} and target opponent discards their hand."));

    }

    private BrinkOfMadness(final BrinkOfMadness card) {
        super(card);
    }

    @Override
    public BrinkOfMadness copy() {
        return new BrinkOfMadness(this);
    }

    static class BrinkOfMadnessEffect extends OneShotEffect {

        private BrinkOfMadnessEffect() {
            super(Outcome.Benefit);
            this.staticText = "Target player discards their hand";
        }

        private BrinkOfMadnessEffect(final BrinkOfMadnessEffect effect) {
            super(effect);
        }

        @Override
        public BrinkOfMadnessEffect copy() {
            return new BrinkOfMadnessEffect(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Player player = game.getPlayer(source.getFirstTarget());
            return player != null && !player.discard(player.getHand(), false, source, game).isEmpty();
        }
    }
}
