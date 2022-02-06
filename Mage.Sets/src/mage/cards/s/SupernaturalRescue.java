package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SupernaturalRescue extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.SPIRIT);
    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);
    private static final Hint hint = new ConditionHint(condition, "You control a Spirit");

    public SupernaturalRescue(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");

        this.subtype.add(SubType.AURA);

        // This spell has flash as long as you control a Spirit.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL,
                new ConditionalContinuousEffect(new GainAbilitySourceEffect(
                        FlashAbility.getInstance(), Duration.WhileOnBattlefield, true
                ), condition, "this spell has flash as long as you control a Spirit")
        ).setRuleAtTheTop(true).addHint(hint));

        // When you cast this spell, tap up to two target creatures you don't control.
        Ability ability = new CastSourceTriggeredAbility(new TapTargetEffect().setText("tap up to two target creatures you don't control"));
        ability.addTarget(new TargetPermanent(0, 2, StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
        this.addAbility(ability);

        // Enchant creature you control
        TargetPermanent auraTarget = new TargetControlledCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));

        // Enchanted creature gets +1/+2.
        this.addAbility(new SimpleStaticAbility(new BoostEnchantedEffect(1, 2)));
    }

    private SupernaturalRescue(final SupernaturalRescue card) {
        super(card);
    }

    @Override
    public SupernaturalRescue copy() {
        return new SupernaturalRescue(this);
    }
}
