package mage.cards.c;

import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.SpellsCostIncreasingAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;

import java.util.UUID;

/**
 * @author Quercitron
 */
public final class Chill extends CardImpl {

    private static final FilterCard filter = new FilterCard("Red spells");

    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
    }

    public Chill(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        // Red spells cost {2} more to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpellsCostIncreasingAllEffect(2, filter, TargetController.ANY)));
    }

    private Chill(final Chill card) {
        super(card);
    }

    @Override
    public Chill copy() {
        return new Chill(this);
    }
}
