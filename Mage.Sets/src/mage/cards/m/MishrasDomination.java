package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.AttachedToMatchesFilterCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.decorator.ConditionalRestrictionEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.CantBlockAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
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
public final class MishrasDomination extends CardImpl {

    private static final Condition condition
            = new AttachedToMatchesFilterCondition(StaticFilters.FILTER_CONTROLLED_PERMANENT);

    public MishrasDomination(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // As long as you control enchanted creature, it gets +2/+2. Otherwise, it can't block.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostEnchantedEffect(2, 2), condition,
                "as long as you control enchanted creature, it gets +2/+2"
        ));
        ability.addEffect(new ConditionalRestrictionEffect(
                new CantBlockAttachedEffect(AttachmentType.AURA),
                new InvertCondition(condition), "otherwise, it can't block"
        ));
        this.addAbility(ability);
    }

    private MishrasDomination(final MishrasDomination card) {
        super(card);
    }

    @Override
    public MishrasDomination copy() {
        return new MishrasDomination(this);
    }
}
