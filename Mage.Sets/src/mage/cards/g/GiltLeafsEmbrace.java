package mage.cards.g;

import java.util.UUID;
import mage.constants.SubType;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.constants.Outcome;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class GiltLeafsEmbrace extends CardImpl {

    public GiltLeafsEmbrace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        this.subtype.add(SubType.AURA);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // When this Aura enters, enchanted creature gains trample and indestructible until end of turn.
        EntersBattlefieldTriggeredAbility ability = new EntersBattlefieldTriggeredAbility(
            new GainAbilityAttachedEffect(TrampleAbility.getInstance(), AttachmentType.AURA, Duration.EndOfTurn)
                .setText("enchanted creature gains trample")
        );
        ability.addEffect(
            new GainAbilityAttachedEffect(IndestructibleAbility.getInstance(), AttachmentType.AURA, Duration.EndOfTurn)
                .setText("and indestructible until end of turn")
        );
        this.addAbility(ability);

        // Enchanted creature gets +2/+0.
        this.addAbility(new SimpleStaticAbility(new BoostEnchantedEffect(2, 0)));
    }

    private GiltLeafsEmbrace(final GiltLeafsEmbrace card) {
        super(card);
    }

    @Override
    public GiltLeafsEmbrace copy() {
        return new GiltLeafsEmbrace(this);
    }
}
