
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author North
 */
public final class TimbermawLarva extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("Forest you control");

    static {
        filter.add(new SubtypePredicate(SubType.FOREST));
        filter.add(new ControllerPredicate(TargetController.YOU));

    }

    public TimbermawLarva(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        PermanentsOnBattlefieldCount value = new PermanentsOnBattlefieldCount(filter);
        this.addAbility(new AttacksTriggeredAbility(new BoostSourceEffect(value, value, Duration.EndOfTurn), false));
    }

    public TimbermawLarva(final TimbermawLarva card) {
        super(card);
    }

    @Override
    public TimbermawLarva copy() {
        return new TimbermawLarva(this);
    }
}
