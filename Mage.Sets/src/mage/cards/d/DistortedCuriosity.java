package mage.cards.d;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CorruptedCondition;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DistortedCuriosity extends CardImpl {

    public DistortedCuriosity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}");

        // Corrupted -- This spell costs {2} less to cast if an opponent has three or more poison counters.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(2, CorruptedCondition.instance)
        ).setAbilityWord(AbilityWord.CORRUPTED).setRuleAtTheTop(true).addHint(CorruptedCondition.getHint()));

        // Draw two cards.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));
    }

    private DistortedCuriosity(final DistortedCuriosity card) {
        super(card);
    }

    @Override
    public DistortedCuriosity copy() {
        return new DistortedCuriosity(this);
    }
}
