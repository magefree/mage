package mage.cards.s;

import java.util.UUID;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.AttachedToTappedCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.constants.Outcome;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.ShroudAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;

/**
 *
 * @author TheElk801
 */
public final class SpectralCloak extends CardImpl {

    public SpectralCloak(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}{U}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature has shroud as long as it's untapped.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilityAttachedEffect(ShroudAbility.getInstance(), AttachmentType.AURA),
                AttachedToTappedCondition.UNTAPPED,
                "Enchanted creature has shroud as long as it's untapped."
        )));
    }

    private SpectralCloak(final SpectralCloak card) {
        super(card);
    }

    @Override
    public SpectralCloak copy() {
        return new SpectralCloak(this);
    }
}
