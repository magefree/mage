
package mage.cards.i;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.PreventDamageToAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author L_J
 */
public final class Inviolability extends CardImpl {

    public Inviolability(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Benefit));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));

        // Prevent all damage that would be dealt to enchanted creature.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PreventDamageToAttachedEffect(Duration.WhileOnBattlefield, AttachmentType.EQUIPMENT, false)));
    }

    public Inviolability(final Inviolability card) {
        super(card);
    }

    @Override
    public Inviolability copy() {
        return new Inviolability(this);
    }
}
