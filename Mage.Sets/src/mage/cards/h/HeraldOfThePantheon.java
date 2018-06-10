
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.CardTypePredicate;

/**
 *
 * @author fireshoes
 */
public final class HeraldOfThePantheon extends CardImpl {
    
    private static final FilterCard filter = new FilterCard("Enchantment spells");
    private static final FilterSpell filter2 = new FilterSpell("an enchantment spell");
    
    static {
        filter.add(new CardTypePredicate(CardType.ENCHANTMENT)); 
        filter2.add(new CardTypePredicate(CardType.ENCHANTMENT));
    }

    public HeraldOfThePantheon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.CENTAUR);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Enchantment spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpellsCostReductionControllerEffect(filter, 1)));     
        
        // Whenever you cast an enchantment spell, you gain 1 life.
        this.addAbility(new SpellCastControllerTriggeredAbility(new GainLifeEffect(1), filter2, false));
    }

    public HeraldOfThePantheon(final HeraldOfThePantheon card) {
        super(card);
    }

    @Override
    public HeraldOfThePantheon copy() {
        return new HeraldOfThePantheon(this);
    }
}
