
package mage.cards.s;

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

/**
 *
 * @author North
 */
public final class StinkdrinkerDaredevil extends CardImpl {

    private static final FilterCard filter = new FilterCard("Giant spells");

    static {
        filter.add(SubType.GIANT.getPredicate());
    }

    public StinkdrinkerDaredevil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Giant spells you cast cost {2} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpellsCostReductionControllerEffect(filter, 2)));
    }

    private StinkdrinkerDaredevil(final StinkdrinkerDaredevil card) {
        super(card);
    }

    @Override
    public StinkdrinkerDaredevil copy() {
        return new StinkdrinkerDaredevil(this);
    }
}
