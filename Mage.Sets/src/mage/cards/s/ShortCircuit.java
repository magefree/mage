package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.AttachedToMatchesFilterCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.LoseAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
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
public final class ShortCircuit extends CardImpl {

    private static final Condition condition = new AttachedToMatchesFilterCondition(StaticFilters.FILTER_PERMANENT_CREATURE);

    public ShortCircuit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        this.subtype.add(SubType.AURA);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Enchant artifact or creature
        TargetPermanent auraTarget = new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));

        // As long as enchanted permanent is a creature, it gets -3/-0 and loses flying.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostEnchantedEffect(-3, 0), condition,
                "as long as enchanted permanent is a creature, it gets -3/-0"
        ));
        ability.addEffect(new ConditionalContinuousEffect(
                new LoseAbilityAttachedEffect(
                        FlyingAbility.getInstance(), AttachmentType.AURA
                ), condition, "and loses flying"
        ));
        this.addAbility(ability);
    }

    private ShortCircuit(final ShortCircuit card) {
        super(card);
    }

    @Override
    public ShortCircuit copy() {
        return new ShortCircuit(this);
    }
}
