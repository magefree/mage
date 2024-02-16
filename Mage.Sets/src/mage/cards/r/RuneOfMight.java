package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.AttachedToMatchesFilterCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RuneOfMight extends CardImpl {

    private static final Condition condition1 = new AttachedToMatchesFilterCondition(StaticFilters.FILTER_PERMANENT_CREATURE);
    private static final Condition condition2 = new AttachedToMatchesFilterCondition(StaticFilters.FILTER_PERMANENT_EQUIPMENT);

    public RuneOfMight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");

        this.subtype.add(SubType.AURA);
        this.subtype.add(SubType.RUNE);

        // Enchant permanent
        TargetPermanent auraTarget = new TargetPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // When Rune of Might enters the battlefield, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1)));

        // As long as enchanted permanent is a creature, it gets +1/+1 and has trample.
        ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostEquippedEffect(1, 1), condition1,
                "as long as enchanted permanent is a creature, it gets +1/+1"
        ));
        ability.addEffect(new ConditionalContinuousEffect(new GainAbilityAttachedEffect(
                TrampleAbility.getInstance(), AttachmentType.AURA
        ), condition1, "and has trample"));
        this.addAbility(ability);

        // As long as enchanted permanent is an Equipment, it has "Equipped creature gets +1/+1 and has trample."
        ability = new SimpleStaticAbility(new BoostEquippedEffect(1, 1));
        ability.addEffect(new GainAbilityAttachedEffect(
                TrampleAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText("and has trample"));
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilityAttachedEffect(ability, AttachmentType.AURA),
                condition2, "as long as enchanted permanent is an Equipment, " +
                "it has \"Equipped creature gets +1/+1 and has trample.\""
        )));
    }

    private RuneOfMight(final RuneOfMight card) {
        super(card);
    }

    @Override
    public RuneOfMight copy() {
        return new RuneOfMight(this);
    }
}
