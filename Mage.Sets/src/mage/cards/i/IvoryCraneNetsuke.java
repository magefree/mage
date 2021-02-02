
package mage.cards.i;

import java.util.UUID;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.CardsInHandCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.TargetController;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class IvoryCraneNetsuke extends CardImpl {

    public IvoryCraneNetsuke(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // At the beginning of your upkeep, if you have seven or more cards in hand, you gain 4 life.
        TriggeredAbility ability  = new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new GainLifeEffect(4), TargetController.YOU, false);
        CardsInHandCondition condition = new CardsInHandCondition(ComparisonType.MORE_THAN, 6);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, condition, "At the beginning of your upkeep, if you have seven or more cards in hand, you gain 4 life."));
        
    }

    private IvoryCraneNetsuke(final IvoryCraneNetsuke card) {
        super(card);
    }

    @Override
    public IvoryCraneNetsuke copy() {
        return new IvoryCraneNetsuke(this);
    }
}
