package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class DeathSpark extends CardImpl {

    public DeathSpark(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        // Death Spark deals 1 damage to any target.
        this.getSpellAbility().addEffect(new DamageTargetEffect(1));
        this.getSpellAbility().addTarget(new TargetAnyTarget());

        // At the beginning of your upkeep, if Death Spark is in your graveyard with a creature card directly above it, you may pay {1}. If you do, return Death Spark to your hand.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                Zone.GRAVEYARD, TargetController.YOU,
                new DoIfCostPaid(new ReturnSourceFromGraveyardToHandEffect().setText("return this card to your hand"), new GenericManaCost(1)), false
        ).withInterveningIf(DeathSparkCondition.instance));
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
        if (controller == null) {
            return false;
        }
        boolean nextCard = false;
        for (Card card : controller.getGraveyard().getCards(game)) {
            if (nextCard) {
                return card.isCreature(game);
            }
            if (card.getId().equals(source.getSourceId())) {
                nextCard = true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "this card is in your graveyard with a creature card directly above it";
    }
}
