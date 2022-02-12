package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GeistlightSnare extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.SPIRIT);
    private static final Condition condition1 = new PermanentsOnTheBattlefieldCondition(filter);
    private static final Condition condition2 = new PermanentsOnTheBattlefieldCondition(StaticFilters.FILTER_PERMANENT_ENCHANTMENT);
    private static final Hint hint1 = new ConditionHint(condition1, "You control a Spirit");
    private static final Hint hint2 = new ConditionHint(condition2, "You control an enchantment");

    public GeistlightSnare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // This spell costs {1} less to cast if you control a Spirit. It also costs {1} less to cast if you control an enchantment.
        Ability ability = new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(1, condition1).setCanWorksOnStackOnly(true)
                .setText("this spell costs {1} less to cast if you control a Spirit")
        ).setRuleAtTheTop(true);
        ability.addEffect(new SpellCostReductionSourceEffect(1, condition2).setCanWorksOnStackOnly(true)
                .setText("It also costs {1} less to cast if you control an enchantment"));

        // Counter target spell unless its controller pays {3}.
        this.getSpellAbility().addEffect(new CounterUnlessPaysEffect(new GenericManaCost(3)));
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addHint(hint1);
        this.getSpellAbility().addHint(hint2);
    }

    private GeistlightSnare(final GeistlightSnare card) {
        super(card);
    }

    @Override
    public GeistlightSnare copy() {
        return new GeistlightSnare(this);
    }
}
