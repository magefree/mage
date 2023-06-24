
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.ControlEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.target.TargetPermanent;
import mage.target.common.TargetEnchantmentPermanent;

/**
 *
 * @author Quercitron
 */
public final class StealEnchantment extends CardImpl {

    public StealEnchantment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{U}{U}");
        this.subtype.add(SubType.AURA);


        // Enchant enchantment
        TargetPermanent auraTarget = new TargetEnchantmentPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.GainControl));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // You control enchanted enchantment.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ControlEnchantedEffect("enchantment")));
    }

    private StealEnchantment(final StealEnchantment card) {
        super(card);
    }

    @Override
    public StealEnchantment copy() {
        return new StealEnchantment(this);
    }
}
