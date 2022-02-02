package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

/**
 *
 * @author weirddan455
 */
public final class NezumiBladeblesser extends CardImpl {

    private static final Condition artifactCondition = new PermanentsOnTheBattlefieldCondition(StaticFilters.FILTER_PERMANENT_ARTIFACT);
    private static final Condition enchantmentCondition = new PermanentsOnTheBattlefieldCondition(StaticFilters.FILTER_ENCHANTMENT_PERMANENT);
    private static final Hint artifactHint = new ConditionHint(artifactCondition, "You control an artifact");
    private static final Hint enchantmentHint = new ConditionHint(enchantmentCondition, "You control an enchantment");

    public NezumiBladeblesser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.RAT);
        this.subtype.add(SubType.SAMURAI);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Nezumi Bladeblesser has deathtouch as long as you control an artifact.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(DeathtouchAbility.getInstance()),
                artifactCondition,
                "{this} has deathtouch as long as you control an artifact"
        )).addHint(artifactHint));

        // Nezumi Bladeblesser has menace as long as you control an enchantment.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(new MenaceAbility()),
                enchantmentCondition,
                "{this} has menace as long as you control an enchantment"
        )).addHint(enchantmentHint));
    }

    private NezumiBladeblesser(final NezumiBladeblesser card) {
        super(card);
    }

    @Override
    public NezumiBladeblesser copy() {
        return new NezumiBladeblesser(this);
    }
}
