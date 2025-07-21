
package mage.cards.c;

import mage.abilities.common.BecomesTappedAttachedTriggeredAbility;
import mage.abilities.common.DealtDamageAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.meta.OrTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Cryoshatter
 */
public final class Cryoshatter extends CardImpl {

    public Cryoshatter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.UnboostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature gets -5/-0.
        this.addAbility(new SimpleStaticAbility(new BoostEnchantedEffect(-5, 0)));

        // When enchanted creature becomes tapped or is dealt damage, destroy it.
        this.addAbility(new OrTriggeredAbility(Zone.BATTLEFIELD, new DestroyTargetEffect().setText("destroy it"), false,
                "When enchanted creature becomes tapped or is dealt damage, ",
                new BecomesTappedAttachedTriggeredAbility(null, "enchanted creature", false, SetTargetPointer.PERMANENT),
                new DealtDamageAttachedTriggeredAbility(Zone.BATTLEFIELD, null, false, SetTargetPointer.PERMANENT)));
    }

    private Cryoshatter(final Cryoshatter card) {
        super(card);
    }

    @Override
    public Cryoshatter copy() {
        return new Cryoshatter(this);
    }
}
