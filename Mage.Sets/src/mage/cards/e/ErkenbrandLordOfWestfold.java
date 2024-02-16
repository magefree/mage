package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ErkenbrandLordOfWestfold extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.HUMAN, "Human");

    public ErkenbrandLordOfWestfold(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Erkenbrand, Lord of Westfold or another Human enters the battlefield under your control, creatures you control get +1/+0 until end of turn.
        this.addAbility(new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new BoostControlledEffect(1, 0, Duration.EndOfTurn),
                filter, false, true
        ));
    }

    private ErkenbrandLordOfWestfold(final ErkenbrandLordOfWestfold card) {
        super(card);
    }

    @Override
    public ErkenbrandLordOfWestfold copy() {
        return new ErkenbrandLordOfWestfold(this);
    }
}
