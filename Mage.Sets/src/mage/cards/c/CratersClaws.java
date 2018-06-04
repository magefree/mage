
package mage.cards.c;

import java.util.UUID;
import mage.abilities.condition.common.FerociousCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.IntPlusDynamicValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author emerald000
 */
public final class CratersClaws extends CardImpl {

    public CratersClaws(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{R}");

        // Crater's Claws deals X damage to any target.
        // <i>Ferocious</i> &mdash; Crater's Claws deals X plus 2 damage to that creature or player instead if you control a creature with power 4 or greater.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DamageTargetEffect(new IntPlusDynamicValue(2, new ManacostVariableValue())),
                new DamageTargetEffect(new ManacostVariableValue()),
                FerociousCondition.instance,
                "{this} deals X damage to any target."
                + "<br><i>Ferocious</i> &mdash; {this} deals X plus 2 damage to that permanent or player instead if you control a creature with power 4 or greater"));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    public CratersClaws(final CratersClaws card) {
        super(card);
    }

    @Override
    public CratersClaws copy() {
        return new CratersClaws(this);
    }
}
