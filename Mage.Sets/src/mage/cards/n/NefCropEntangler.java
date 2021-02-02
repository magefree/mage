
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesExertSourceTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.ExertAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author fireshoes
 */
public final class NefCropEntangler extends CardImpl {

    public NefCropEntangler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // You may exert Nef-Crop Entangler as it attacks. When you do, it gets +1/+2 until end of turn.
        this.addAbility(new ExertAbility(new BecomesExertSourceTriggeredAbility(new BoostSourceEffect(1, 2, Duration.EndOfTurn))));
    }

    private NefCropEntangler(final NefCropEntangler card) {
        super(card);
    }

    @Override
    public NefCropEntangler copy() {
        return new NefCropEntangler(this);
    }
}
