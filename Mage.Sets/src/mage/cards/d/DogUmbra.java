package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.AttachedToMatchesFilterCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.decorator.ConditionalRestrictionEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.CantAttackBlockAttachedEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.UmbraArmorAbility;
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
 * @author Susucr
 */
public final class DogUmbra extends CardImpl {

    private static final Condition condition
            = new AttachedToMatchesFilterCondition(StaticFilters.FILTER_CONTROLLED_PERMANENT);

    public DogUmbra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        this.subtype.add(SubType.AURA);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // As long as another player controls enchanted creature, it can't attack or block. Otherwise, Dog Umbra has umbra armor.
        Ability ability = new SimpleStaticAbility(new ConditionalRestrictionEffect(
                new CantAttackBlockAttachedEffect(AttachmentType.AURA),
                new InvertCondition(condition),
                "As long as another player controls enchanted creature, it can't attack or block."
        ));
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(new UmbraArmorAbility()),
                condition,
                "Otherwise, {this} has umbra armor"
        ));
        this.addAbility(ability);
    }

    private DogUmbra(final DogUmbra card) {
        super(card);
    }

    @Override
    public DogUmbra copy() {
        return new DogUmbra(this);
    }
}
