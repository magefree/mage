package mage.cards.v;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VanquishTheHorde extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(StaticFilters.FILTER_PERMANENT_CREATURE);
    private static final Hint hint = new ValueHint("Creatures on the battlefield", xValue);

    public VanquishTheHorde(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{6}{W}{W}");

        // This spell costs {1} less to cast for each creature on the battlefield.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(xValue)
                .setText("this spell costs {1} less to cast for each creature on the battlefield")
        ).addHint(hint).setRuleAtTheTop(true));

        // Destroy all creatures.
        this.getSpellAbility().addEffect(new DestroyAllEffect(StaticFilters.FILTER_PERMANENT_CREATURES));
    }

    private VanquishTheHorde(final VanquishTheHorde card) {
        super(card);
    }

    @Override
    public VanquishTheHorde copy() {
        return new VanquishTheHorde(this);
    }
}
