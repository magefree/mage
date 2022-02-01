package mage.cards.a;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreatureOrPlaneswalker;

/**
 *
 * @author weirddan455
 */
public final class AssassinsInk extends CardImpl {

    public AssassinsInk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}{B}");

        // This spell costs {1} less to cast if you control an artifact and {1} less to cast if you control an enchantment.
        Condition artifactCondition = new PermanentsOnTheBattlefieldCondition(StaticFilters.FILTER_PERMANENT_ARTIFACT);
        Condition enchantmentCondition = new PermanentsOnTheBattlefieldCondition(StaticFilters.FILTER_ENCHANTMENT_PERMANENT);
        Ability ability = new SimpleStaticAbility(Zone.ALL, new SpellCostReductionSourceEffect(1, artifactCondition)
                .setText("This spell costs {1} less to cast if you control an artifact"));
        ability.addEffect(new SpellCostReductionSourceEffect(1, enchantmentCondition)
                .setText("and {1} less to cast if you control an enchantment"));
        ability.addHint(new ConditionHint(artifactCondition, "you control an artifact"));
        ability.addHint(new ConditionHint(enchantmentCondition, "you control an enchantment"));
        ability.setRuleAtTheTop(true);
        this.addAbility(ability);

        // Destroy target creature or planeswalker.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
    }

    private AssassinsInk(final AssassinsInk card) {
        super(card);
    }

    @Override
    public AssassinsInk copy() {
        return new AssassinsInk(this);
    }
}
