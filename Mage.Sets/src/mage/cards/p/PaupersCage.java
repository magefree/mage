
package mage.cards.p;

import java.util.UUID;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.CardsInHandCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.TargetController;
import mage.constants.Zone;

/**
 *
 * @author fireshoes
 */
public final class PaupersCage extends CardImpl {

    public PaupersCage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // At the beginning of each opponent's upkeep, if that player has two or fewer cards in hand, Paupers' Cage deals 2 damage to that player.
        TriggeredAbility ability  = new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, 
                new DamageTargetEffect(2), TargetController.OPPONENT, false, true);
        CardsInHandCondition condition = new CardsInHandCondition(ComparisonType.FEWER_THAN, 3, null, TargetController.ACTIVE);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, condition,
                "At the beginning of each opponent's upkeep, if that player has two or fewer cards in hand, {this} deals 2 damage to that player."));
    }

    private PaupersCage(final PaupersCage card) {
        super(card);
    }

    @Override
    public PaupersCage copy() {
        return new PaupersCage(this);
    }
}
