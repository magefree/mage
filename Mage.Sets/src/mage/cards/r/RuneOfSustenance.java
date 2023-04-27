package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.AttachedToMatchesFilterCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.LifelinkAbility;
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
public final class RuneOfSustenance extends CardImpl {

    private static final Condition condition1 = new AttachedToMatchesFilterCondition(StaticFilters.FILTER_PERMANENT_CREATURE);
    private static final Condition condition2 = new AttachedToMatchesFilterCondition(StaticFilters.FILTER_PERMANENT_EQUIPMENT);

    public RuneOfSustenance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        this.subtype.add(SubType.AURA);
        this.subtype.add(SubType.RUNE);

        // Enchant permanent
        TargetPermanent auraTarget = new TargetPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // When Rune of Sustenance enters the battlefield, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1)));

        // As long as enchanted permanent is a creature, it has lifelink.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilityAttachedEffect(
                        LifelinkAbility.getInstance(), AttachmentType.AURA
                ), condition1, "as long as enchanted permanent is a creature, it has lifelink"
        )));

        // As long as enchanted permanent is an Equipment, it has "Equipped creature has lifelink."
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilityAttachedEffect(
                        new SimpleStaticAbility(new GainAbilityAttachedEffect(
                                LifelinkAbility.getInstance(), AttachmentType.EQUIPMENT
                        )), AttachmentType.AURA
                ), condition2, "as long as enchanted permanent is an Equipment, " +
                "it has \"Equipped creature has lifelink.\""
        )));
    }

    private RuneOfSustenance(final RuneOfSustenance card) {
        super(card);
    }

    @Override
    public RuneOfSustenance copy() {
        return new RuneOfSustenance(this);
    }
}
