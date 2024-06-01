package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.decorator.ConditionalRestrictionEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.CantAttackBlockAttachedEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.TotemArmorAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Susucr
 */
public final class DogUmbra extends CardImpl {

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
                DogUmbraCondition.TRUE,
                "As long as another player controls enchanted creature, it can't attack or block."
        ));
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(new TotemArmorAbility()),
                DogUmbraCondition.FALSE,
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

enum DogUmbraCondition implements Condition {
    TRUE(true),
    FALSE(false);
    private final boolean value;

    DogUmbraCondition(boolean value) {
        this.value = value;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return Optional
                .ofNullable(source.getSourcePermanentIfItStillExists(game))
                .map(Permanent::getAttachedTo)
                .map(game::getPermanentOrLKIBattlefield)
                .map(Controllable::getControllerId)
                .map(source::isControlledBy)
                .orElse(false)
                .equals(!value);
    }
}
