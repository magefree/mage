package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.*;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class GlasswingGrace extends ModalDoubleFacedCard {

    public GlasswingGrace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.ENCHANTMENT}, new SubType[]{SubType.AURA}, "{3}{W/B}{W/B}",
                "Age-Graced Chapel", new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // 1.
        // Glasswing Grace
        // Enchantment - Aura

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getLeftHalfCard().getSpellAbility().addTarget(auraTarget);
        this.getLeftHalfCard().getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.getLeftHalfCard().addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature gets +2/+2 and has flying and lifelink.
        Ability ability = new SimpleStaticAbility(new BoostEnchantedEffect(2, 2));
        ability.addEffect(
                new GainAbilityAttachedEffect(FlyingAbility.getInstance(), AttachmentType.AURA)
                        .setText("and has flying")
        );
        ability.addEffect(
                new GainAbilityAttachedEffect(LifelinkAbility.getInstance(), AttachmentType.AURA)
                        .setText("and lifelink")
        );
        this.getLeftHalfCard().addAbility(ability);

        // 2.
        // Ace-Graced Chapel
        // Land

        // Ace-Graced Chapel enters the battlefield tapped.
        this.getRightHalfCard().addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {W} or {B}.
        this.getRightHalfCard().addAbility(new WhiteManaAbility());
        this.getRightHalfCard().addAbility(new BlackManaAbility());
    }

    private GlasswingGrace(final GlasswingGrace card) {
        super(card);
    }

    @Override
    public GlasswingGrace copy() {
        return new GlasswingGrace(this);
    }
}
