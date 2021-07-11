
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author emerald000
 */
public final class DeathSpark extends CardImpl {

    public DeathSpark(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");


        // Death Spark deals 1 damage to any target.
        this.getSpellAbility().addEffect(new DamageTargetEffect(1));
        this.getSpellAbility().addTarget(new TargetAnyTarget());

        // At the beginning of your upkeep, if Death Spark is in your graveyard with a creature card directly above it, you may pay {1}. If you do, return Death Spark to your hand.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(
                        Zone.GRAVEYARD,
                        new DoIfCostPaid(new ReturnSourceFromGraveyardToHandEffect(), new GenericManaCost(1)),
                        TargetController.YOU,
                        false),
                DeathSparkCondition.instance,
                "At the beginning of your upkeep, if {this} is in your graveyard with a creature card directly above it, you may pay {1}. If you do, return {this} to your hand."));
    }

    private DeathSpark(final DeathSpark card) {
        super(card);
    }

    @Override
    public DeathSpark copy() {
        return new DeathSpark(this);
    }
}

enum DeathSparkCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            boolean nextCard = false;
            for (Card card : controller.getGraveyard().getCards(game)) {
                if (nextCard) {
                    return card.isCreature(game);
                }
                if (card.getId().equals(source.getSourceId())) {
                    nextCard = true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "{this} is in your graveyard with a creature card directly above it";
    }
}
