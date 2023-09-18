
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CantBeTargetedAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterObject;
import mage.filter.FilterStackObject;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author fireshoes
 */
public final class SpellbaneCentaur extends CardImpl {

    private static final FilterObject filter = new FilterStackObject("blue spells or abilities from blue sources");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
    }

    public SpellbaneCentaur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.CENTAUR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Creatures you control can't be the targets of blue spells or abilities from blue sources.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new CantBeTargetedAllEffect(StaticFilters.FILTER_CONTROLLED_CREATURES,
                        filter, Duration.WhileOnBattlefield)));
    }

    private SpellbaneCentaur(final SpellbaneCentaur card) {
        super(card);
    }

    @Override
    public SpellbaneCentaur copy() {
        return new SpellbaneCentaur(this);
    }
}
