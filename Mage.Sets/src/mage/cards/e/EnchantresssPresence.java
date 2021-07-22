
package mage.cards.e;

import java.util.UUID;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterSpell;

/**
 *
 * @author Plopman
 */
public final class EnchantresssPresence extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("an enchantment spell");

    static {
        filter.add(CardType.ENCHANTMENT.getPredicate());
    }

    
    public EnchantresssPresence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{G}");


        // Whenever you cast an enchantment spell, draw a card.
        this.addAbility(new SpellCastControllerTriggeredAbility(new DrawCardSourceControllerEffect(1), filter, false));
    }

    private EnchantresssPresence(final EnchantresssPresence card) {
        super(card);
    }

    @Override
    public EnchantresssPresence copy() {
        return new EnchantresssPresence(this);
    }
}
