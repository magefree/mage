package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.AttachedToMatchesFilterCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.WardAbility;
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
public final class CombatResearch extends CardImpl {

    private static final Condition condition
            = new AttachedToMatchesFilterCondition(StaticFilters.FILTER_PERMANENT_LEGENDARY);

    public CombatResearch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature has "Whenever this creature deals combat damage to a player, draw a card."
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(
                new DealsCombatDamageToAPlayerTriggeredAbility(
                        new DrawCardSourceControllerEffect(1), false
                ), AttachmentType.AURA
        )));

        // As long as enchanted creature is legendary, it gets +1/+1 and has ward {1}.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostEnchantedEffect(1, 1), condition,
                "as long as enchanted creature is legendary, it gets +1/+1"
        ));
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilityAttachedEffect(
                        new WardAbility(new GenericManaCost(1), false), AttachmentType.AURA
                ), condition, "and has ward {1}. <i>(Whenever enchanted creature becomes the target " +
                "of a spell or ability an opponent controls, counter it unless that player pays {1})</i>"
        ));
        this.addAbility(ability);
    }

    private CombatResearch(final CombatResearch card) {
        super(card);
    }

    @Override
    public CombatResearch copy() {
        return new CombatResearch(this);
    }
}
