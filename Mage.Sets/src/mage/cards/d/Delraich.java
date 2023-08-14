
package mage.cards.d;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Delraich extends CardImpl {

    private static final FilterControlledCreaturePermanent filter
            = new FilterControlledCreaturePermanent("black creatures");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public Delraich(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{B}");

        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // You may sacrifice three black creatures rather than pay Delraich's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new SacrificeTargetCost(
                new TargetControlledPermanent(3, 3, filter, false)
        )));

        // Trample
        this.addAbility(TrampleAbility.getInstance());
    }

    private Delraich(final Delraich card) {
        super(card);
    }

    @Override
    public Delraich copy() {
        return new Delraich(this);
    }
}
