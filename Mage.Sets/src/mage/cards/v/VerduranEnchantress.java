
package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.CardTypePredicate;

import java.util.UUID;

/**
 *
 * @author Plopman
 */
public final class VerduranEnchantress extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("an enchantment spell");

    static {
        filter.add(new CardTypePredicate(CardType.ENCHANTMENT));
    }
    
    public VerduranEnchantress(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);

        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        // Whenever you cast an enchantment spell, you may draw a card.
        this.addAbility(new SpellCastControllerTriggeredAbility(new DrawCardSourceControllerEffect(1), filter, true));
    }

    public VerduranEnchantress(final VerduranEnchantress card) {
        super(card);
    }

    @Override
    public VerduranEnchantress copy() {
        return new VerduranEnchantress(this);
    }
}
