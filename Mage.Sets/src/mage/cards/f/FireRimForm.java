package mage.cards.f;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FireRimForm extends CardImpl {

    public FireRimForm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");

        this.subtype.add(SubType.AURA);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // When this Aura enters, enchanted creature gains first strike until end of turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainAbilityAttachedEffect(
                FirstStrikeAbility.getInstance(), AttachmentType.AURA, Duration.EndOfTurn
        )));

        // Enchanted creature gets +2/+0.
        this.addAbility(new SimpleStaticAbility(new BoostEnchantedEffect(2, 0)));
    }

    private FireRimForm(final FireRimForm card) {
        super(card);
    }

    @Override
    public FireRimForm copy() {
        return new FireRimForm(this);
    }
}
