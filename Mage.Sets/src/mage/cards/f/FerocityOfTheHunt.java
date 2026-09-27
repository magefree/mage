package mage.cards.f;

import java.util.UUID;
import mage.constants.SubType;
import mage.abilities.keyword.FlashAbility;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlTargetEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.target.TargetPermanent;
import mage.abilities.keyword.DeathtouchAbility;
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
public final class FerocityOfTheHunt extends CardImpl {

    public FerocityOfTheHunt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B/G}");

        this.subtype.add(SubType.AURA);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature gets +1/+0 and has deathtouch.
        Ability ability = new SimpleStaticAbility(new BoostEnchantedEffect(1, 0, Duration.WhileOnBattlefield));
        ability.addEffect(new GainAbilityAttachedEffect(
            DeathtouchAbility.getInstance(),
            AttachmentType.AURA, Duration.WhileOnBattlefield,
            "and has deathtouch"
        ));
        this.addAbility(ability);

        // When enchanted creature dies, return that card to the battlefield tapped under its owner's control.
        this.addAbility(new DiesAttachedTriggeredAbility(
            new ReturnToBattlefieldUnderOwnerControlTargetEffect(true, false),
            "enchanted creature", false, true, SetTargetPointer.CARD
        ));
    }

    private FerocityOfTheHunt(final FerocityOfTheHunt card) {
        super(card);
    }

    @Override
    public FerocityOfTheHunt copy() {
        return new FerocityOfTheHunt(this);
    }
}
