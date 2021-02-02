
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandChosenControlledPermanentEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 * @author noxx
 */
public final class StampedingSerow extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("green creature you control");

    static {
        filter.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public StampedingSerow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{G}");
        this.subtype.add(SubType.ANTELOPE);
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(5);
        this.toughness = new MageInt(4);
        this.addAbility(TrampleAbility.getInstance());
        // At the beginning of your upkeep, return a green creature you control to its owner's hand.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new ReturnToHandChosenControlledPermanentEffect(filter), TargetController.YOU, false));
    }

    private StampedingSerow(final StampedingSerow card) {
        super(card);
    }

    @Override
    public StampedingSerow copy() {
        return new StampedingSerow(this);
    }
}
