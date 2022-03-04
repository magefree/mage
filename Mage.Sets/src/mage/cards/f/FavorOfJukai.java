package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.AttachedToMatchesFilterCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.ChannelAbility;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FavorOfJukai extends CardImpl {

    private static final Condition condition = new AttachedToMatchesFilterCondition(StaticFilters.FILTER_PERMANENT_CREATURE);

    public FavorOfJukai(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");

        this.subtype.add(SubType.AURA);

        // Enchant artifact or creature
        TargetPermanent auraTarget = new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));

        // As long as enchanted permanent is a creature, it gets +3/+3 and has reach.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostEnchantedEffect(3, 3), condition,
                "as long as enchanted permanent is a creature, it gets +3/+3"
        ));
        ability.addEffect(new ConditionalContinuousEffect(new GainAbilityAttachedEffect(
                ReachAbility.getInstance(), AttachmentType.AURA
        ), condition, "and has reach"));
        this.addAbility(ability);

        // Channel — {1}{G}, Discard Favor of Jukai: Target creature gets +3/+3 and gains reach until end of turn.
        ability = new ChannelAbility(
                "{1}{G}", new BoostTargetEffect(3, 3).setText("target creature gets +3/+3")
        );
        ability.addEffect(new GainAbilityTargetEffect(
                ReachAbility.getInstance()
        ).setText("and gains reach until end of turn"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private FavorOfJukai(final FavorOfJukai card) {
        super(card);
    }

    @Override
    public FavorOfJukai copy() {
        return new FavorOfJukai(this);
    }
}
