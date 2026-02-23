package mage.cards.a;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AquitectsDefenses extends CardImpl {

    public AquitectsDefenses(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        this.subtype.add(SubType.AURA);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Enchant creature you control
        TargetPermanent auraTarget = new TargetControlledCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // When this Aura enters, enchanted creature gains hexproof until end of turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainAbilityAttachedEffect(
                HexproofAbility.getInstance(), AttachmentType.AURA, Duration.EndOfTurn
        )));

        // Enchanted creature gets +1/+2.
        this.addAbility(new SimpleStaticAbility(new BoostEnchantedEffect(1, 2)));
    }

    private AquitectsDefenses(final AquitectsDefenses card) {
        super(card);
    }

    @Override
    public AquitectsDefenses copy() {
        return new AquitectsDefenses(this);
    }
}
