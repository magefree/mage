package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.LandfallAbility;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TifaLockhart extends CardImpl {

    public TifaLockhart(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Landfall -- Whenever a land you control enters, double Tifa Lockhart's power until end of turn.
        this.addAbility(new LandfallAbility(new BoostSourceEffect(
                SourcePermanentPowerValue.ALLOW_NEGATIVE,
                StaticValue.get(0), Duration.EndOfTurn
        ).setText("double {this}'s power until end of turn")));
    }

    private TifaLockhart(final TifaLockhart card) {
        super(card);
    }

    @Override
    public TifaLockhart copy() {
        return new TifaLockhart(this);
    }
}
