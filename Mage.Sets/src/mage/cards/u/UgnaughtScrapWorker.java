
package mage.cards.u;

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
 * @author Styxo
 */
public final class UgnaughtScrapWorker extends CardImpl {

    private static final FilterCard filter = new FilterCard("Droid spells");

    static {
        filter.add(SubType.DROID.getPredicate());
    }

    public UgnaughtScrapWorker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.UGNAUGHT);
        this.subtype.add(SubType.ARTIFICIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Droid spell cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpellsCostReductionControllerEffect(filter, 1)));
    }

    private UgnaughtScrapWorker(final UgnaughtScrapWorker card) {
        super(card);
    }

    @Override
    public UgnaughtScrapWorker copy() {
        return new UgnaughtScrapWorker(this);
    }
}
