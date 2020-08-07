package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PerimeterSergeant extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.HUMAN, "Humans");

    public PerimeterSergeant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever Perimeter Sergeant attacks, other Humans you control get +1/+0 until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new BoostControlledEffect(
                1, 0, Duration.EndOfTurn, filter, true
        ), false));
    }

    private PerimeterSergeant(final PerimeterSergeant card) {
        super(card);
    }

    @Override
    public PerimeterSergeant copy() {
        return new PerimeterSergeant(this);
    }
}
