
package mage.cards.d;

import java.util.UUID;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfYourEndStepTriggeredAbility;
import mage.abilities.condition.common.CreatureCountCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.game.permanent.token.DemonToken;

/**
 * @author noxx
 */
public final class DemonicRising extends CardImpl {

    private static final String ruleText = "At the beginning of your end step, if you control exactly one creature, create a 5/5 black Demon creature token with flying";

    public DemonicRising(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{B}{B}");

        // At the beginning of your end step, if you control exactly one creature, create a 5/5 black Demon creature token with flying.
        TriggeredAbility ability = new BeginningOfYourEndStepTriggeredAbility(new CreateTokenEffect(new DemonToken()), false);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, new CreatureCountCondition(1, TargetController.YOU), ruleText));
    }

    private DemonicRising(final DemonicRising card) {
        super(card);
    }

    @Override
    public DemonicRising copy() {
        return new DemonicRising(this);
    }
}
