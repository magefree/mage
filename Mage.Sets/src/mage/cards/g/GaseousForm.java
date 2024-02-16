
package mage.cards.g;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.PreventAllDamageToAndByAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Quercitron
 */
public final class GaseousForm extends CardImpl {

    public GaseousForm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Neutral));
        this.addAbility(new EnchantAbility(auraTarget));

        // Prevent all combat damage that would be dealt to and dealt by enchanted creature.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PreventAllDamageToAndByAttachedEffect(Duration.WhileOnBattlefield, "enchanted creature", true)));
    }

    private GaseousForm(final GaseousForm card) {
        super(card);
    }

    @Override
    public GaseousForm copy() {
        return new GaseousForm(this);
    }
}
