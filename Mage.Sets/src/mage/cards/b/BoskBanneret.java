
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;

/**
 *
 * @author North
 */
public final class BoskBanneret extends CardImpl {

    private static final FilterCard filter = new FilterCard("Treefolk spells and Shaman spells");

    static {
        filter.add(Predicates.or(
                SubType.TREEFOLK.getPredicate(),
                SubType.SHAMAN.getPredicate()));
    }

    public BoskBanneret(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.TREEFOLK, SubType.SHAMAN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Treefolk spells and Shaman spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpellsCostReductionControllerEffect(filter, 1)));
    }

    private BoskBanneret(final BoskBanneret card) {
        super(card);
    }

    @Override
    public BoskBanneret copy() {
        return new BoskBanneret(this);
    }
}
