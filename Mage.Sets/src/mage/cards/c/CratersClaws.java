package mage.cards.c;

import mage.abilities.condition.common.FerociousCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.IntPlusDynamicValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.hint.common.FerociousHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class CratersClaws extends CardImpl {

    public CratersClaws(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{R}");

        // Crater's Claws deals X damage to any target.
        // <i>Ferocious</i> &mdash; Crater's Claws deals X plus 2 damage to that creature or player instead if you control a creature with power 4 or greater.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DamageTargetEffect(new IntPlusDynamicValue(2, ManacostVariableValue.REGULAR)),
                new DamageTargetEffect(ManacostVariableValue.REGULAR),
                FerociousCondition.instance,
                "{this} deals X damage to any target."
                        + "<br><i>Ferocious</i> &mdash; {this} deals X plus 2 damage instead if you control a creature with power 4 or greater"));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        this.getSpellAbility().addHint(FerociousHint.instance);
    }

    private CratersClaws(final CratersClaws card) {
        super(card);
    }

    @Override
    public CratersClaws copy() {
        return new CratersClaws(this);
    }
}
