package mage.cards.t;

import mage.abilities.TriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInHandCondition;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.target.common.TargetOpponentOrPlaneswalker;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class Thumbscrews extends CardImpl {

    private static final Condition condition = new CardsInHandCondition(ComparisonType.MORE_THAN, 4);

    public Thumbscrews(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // At the beginning of your upkeep, if you have five or more cards in hand, Thumbscrews deals 1 damage to target opponent.
        TriggeredAbility ability = new BeginningOfUpkeepTriggeredAbility(new DamageTargetEffect(1)).withInterveningIf(condition);
        ability.addTarget(new TargetOpponentOrPlaneswalker());
        this.addAbility(ability);
    }

    private Thumbscrews(final Thumbscrews card) {
        super(card);
    }

    @Override
    public Thumbscrews copy() {
        return new Thumbscrews(this);
    }
}
