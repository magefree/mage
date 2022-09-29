package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.AddCardSuperTypeAttachedEffect;
import mage.abilities.effects.mana.AddManaAnyColorAttachedControllerEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.mana.EnchantedTappedTriggeredManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GlitteringFrost extends CardImpl {

    public GlitteringFrost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        this.addSuperType(SuperType.SNOW);
        this.subtype.add(SubType.AURA);

        // Enchant land
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted land is snow.
        this.addAbility(new SimpleStaticAbility(new AddCardSuperTypeAttachedEffect(
                SuperType.SNOW, Duration.WhileOnBattlefield, AttachmentType.AURA
        ).setText("enchanted land is snow")));

        // Whenever enchanted land is tapped for mana, its controller adds an additional one mana of any color.
        this.addAbility(new EnchantedTappedTriggeredManaAbility(new AddManaAnyColorAttachedControllerEffect()
                .setText("its controller adds an additional one mana of any color")));
    }

    private GlitteringFrost(final GlitteringFrost card) {
        super(card);
    }

    @Override
    public GlitteringFrost copy() {
        return new GlitteringFrost(this);
    }
}
