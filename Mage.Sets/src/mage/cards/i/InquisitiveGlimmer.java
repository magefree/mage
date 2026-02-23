package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.RoomUnlockAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.AbilitiesCostReductionControllerEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterEnchantmentCard;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class InquisitiveGlimmer extends CardImpl {

    private static final FilterCard filter = new FilterEnchantmentCard("enchantment spells");

    public InquisitiveGlimmer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{W}{U}");
        
        this.subtype.add(SubType.FOX);
        this.subtype.add(SubType.GLIMMER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Enchantment spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(filter, 1)));

        // Unlock costs you pay cost {1} less.
        this.addAbility(new SimpleStaticAbility(new AbilitiesCostReductionControllerEffect(RoomUnlockAbility.class, "Unlock")));

    }

    private InquisitiveGlimmer(final InquisitiveGlimmer card) {
        super(card);
    }

    @Override
    public InquisitiveGlimmer copy() {
        return new InquisitiveGlimmer(this);
    }
}
