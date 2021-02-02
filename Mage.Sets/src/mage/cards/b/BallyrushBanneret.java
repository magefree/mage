
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
public final class BallyrushBanneret extends CardImpl {

    private static final FilterCard filter = new FilterCard("Kithkin spells and Soldier spells");

    static {
        filter.add(Predicates.or(
                SubType.KITHKIN.getPredicate(),
                SubType.SOLDIER.getPredicate()));
    }

    public BallyrushBanneret(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.KITHKIN, SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Kithkin spells and Soldier spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpellsCostReductionControllerEffect(filter, 1)));
    }

    private BallyrushBanneret(final BallyrushBanneret card) {
        super(card);
    }

    @Override
    public BallyrushBanneret copy() {
        return new BallyrushBanneret(this);
    }
}
