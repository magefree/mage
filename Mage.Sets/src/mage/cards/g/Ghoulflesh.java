
package mage.cards.g;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.AddCardColorAttachedEffect;
import mage.abilities.effects.common.continuous.AddCardSubtypeAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author noxx
 */
public final class Ghoulflesh extends CardImpl {

    public Ghoulflesh(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{B}");
        this.subtype.add(SubType.AURA);


        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.UnboostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature gets -1/-1 and is a black Zombie in addition to its other colors and types.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(-1, -1, Duration.WhileOnBattlefield)));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AddCardSubtypeAttachedEffect(SubType.ZOMBIE, Duration.WhileOnBattlefield, AttachmentType.AURA)));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AddCardColorAttachedEffect(ObjectColor.BLACK, Duration.WhileOnBattlefield, AttachmentType.AURA)));
    }

    private Ghoulflesh(final Ghoulflesh card) {
        super(card);
    }

    @Override
    public Ghoulflesh copy() {
        return new Ghoulflesh(this);
    }
}
