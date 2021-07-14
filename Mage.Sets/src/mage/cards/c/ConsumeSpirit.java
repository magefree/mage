
package mage.cards.c;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.VariableCost;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterMana;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author nantuko
 */
public final class ConsumeSpirit extends CardImpl {

    private static final FilterMana filterBlack = new FilterMana();

    static {
        filterBlack.setBlack(true);
    }

    public ConsumeSpirit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{1}{B}");

        // Spend only black mana on X.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new InfoEffect("Spend only black mana on X")).setRuleAtTheTop(true)
        );

        // Consume Spirit deals X damage to any target and you gain X life.
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        this.getSpellAbility().addEffect(new DamageTargetEffect(ManacostVariableValue.REGULAR));
        this.getSpellAbility().addEffect(new GainLifeEffect(ManacostVariableValue.REGULAR).concatBy("and"));
        VariableCost variableCost = this.getSpellAbility().getManaCostsToPay().getVariableCosts().get(0);
        if (variableCost instanceof VariableManaCost) {
            ((VariableManaCost) variableCost).setFilter(filterBlack);
        }
    }

    private ConsumeSpirit(final ConsumeSpirit card) {
        super(card);
    }

    @Override
    public ConsumeSpirit copy() {
        return new ConsumeSpirit(this);
    }
}
