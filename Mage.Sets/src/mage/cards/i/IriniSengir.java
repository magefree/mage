package mage.cards.i;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.SpellsCostIncreasingAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterEnchantmentCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class IriniSengir extends CardImpl {

    private static final FilterEnchantmentCard filter = new FilterEnchantmentCard("Green enchantment spells and white enchantment spells");

    static {
        filter.add(Predicates.or(new ColorPredicate(ObjectColor.GREEN),
                (new ColorPredicate(ObjectColor.WHITE))));
    }

    public IriniSengir(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.DWARF);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Green enchantment spells and white enchantment spells cost {2} more to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new SpellsCostIncreasingAllEffect(2, filter, TargetController.ANY))
        );
    }

    private IriniSengir(final IriniSengir card) {
        super(card);
    }

    @Override
    public IriniSengir copy() {
        return new IriniSengir(this);
    }
}
