package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterLandPermanent;

import java.util.UUID;

/**
 * @author North
 */
public final class TimbermawLarva extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("Forest you control");

    static {
        filter.add(SubType.FOREST.getPredicate());
        filter.add(TargetController.YOU.getControllerPredicate());

    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);

    public TimbermawLarva(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(new AttacksTriggeredAbility(new BoostSourceEffect(
                xValue, xValue, Duration.EndOfTurn, true, "it"
        ), false));
    }

    private TimbermawLarva(final TimbermawLarva card) {
        super(card);
    }

    @Override
    public TimbermawLarva copy() {
        return new TimbermawLarva(this);
    }
}
