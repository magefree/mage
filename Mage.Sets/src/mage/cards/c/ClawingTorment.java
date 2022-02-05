package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.AttachedToMatchesFilterCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.combat.CantBlockAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ClawingTorment extends CardImpl {

    private static final Condition condition = new AttachedToMatchesFilterCondition(StaticFilters.FILTER_PERMANENT_CREATURE);

    public ClawingTorment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{B}");

        this.subtype.add(SubType.AURA);

        // Enchant artifact creature
        TargetPermanent auraTarget = new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));

        // As long as enchanted permanent is a creature, it gets -1/-1 and can't block.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostEnchantedEffect(-1, -1), condition,
                "as long as enchanted permanent is a creature, it gets -1/-1"
        ));
        ability.addEffect(new ConditionalContinuousEffect(
                new CantBlockAttachedEffect(AttachmentType.AURA), condition, "and can't block"
        ));
        this.addAbility(ability);

        // Enchanted permanent has "At the beginning of your upkeep, you lose 1 life."
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(
                new BeginningOfUpkeepTriggeredAbility(
                        new LoseLifeSourceControllerEffect(1), TargetController.YOU, false
                ), AttachmentType.AURA, Duration.WhileOnBattlefield,
                "enchanted permanent has \"At the beginning of your upkeep, you lose 1 life.\""
        )));
    }

    private ClawingTorment(final ClawingTorment card) {
        super(card);
    }

    @Override
    public ClawingTorment copy() {
        return new ClawingTorment(this);
    }
}
