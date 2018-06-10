
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.cost.SpellsCostIncreasementControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author Quercitron
 */
public final class Derelor extends CardImpl {

    private static final FilterCard filter = new FilterCard("Black spells");
    
    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }
    
    public Derelor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.THRULL);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Black spells you cast cost {B} more to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpellsCostIncreasementControllerEffect(filter, new ManaCostsImpl<>("{B}"))));
    }

    public Derelor(final Derelor card) {
        super(card);
    }

    @Override
    public Derelor copy() {
        return new Derelor(this);
    }
}
