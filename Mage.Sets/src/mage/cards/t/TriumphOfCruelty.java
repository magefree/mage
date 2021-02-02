
package mage.cards.t;

import java.util.UUID;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.ControlsCreatureGreatestPowerCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.target.Target;
import mage.target.common.TargetOpponent;

/**
 *
 * @author noxx
 */
public final class TriumphOfCruelty extends CardImpl {

    private static final String ruleText = "target opponent discards a card if you control the creature with the greatest power or tied for the greatest power";

    public TriumphOfCruelty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{B}");


        // At the beginning of your upkeep, target opponent discards a card if you control the creature with the greatest power or tied for the greatest power.
        TriggeredAbility ability = new BeginningOfUpkeepTriggeredAbility(new DiscardTargetEffect(1), TargetController.YOU, false);
        Target target =  new TargetOpponent();
        ability.addTarget(target);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, ControlsCreatureGreatestPowerCondition.instance, ruleText));
    }

    private TriumphOfCruelty(final TriumphOfCruelty card) {
        super(card);
    }

    @Override
    public TriumphOfCruelty copy() {
        return new TriumphOfCruelty(this);
    }
}
