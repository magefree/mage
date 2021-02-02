
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandChosenControlledPermanentEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author Loki
 */
public final class OniOfWildPlaces extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("red creature you control");

    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
    }

    public OniOfWildPlaces(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{R}");
        this.subtype.add(SubType.DEMON);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(6);
        this.toughness = new MageInt(5);
        this.addAbility(HasteAbility.getInstance());
        // At the beginning of your upkeep, return a red creature you control to its owner's hand.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new ReturnToHandChosenControlledPermanentEffect(filter), TargetController.YOU, false));
    }

    private OniOfWildPlaces(final OniOfWildPlaces card) {
        super(card);
    }

    @Override
    public OniOfWildPlaces copy() {
        return new OniOfWildPlaces(this);
    }
}
