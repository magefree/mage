
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author LoneFox
 */
public final class DefenderOfTheOrder extends CardImpl {

    public DefenderOfTheOrder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Morph {W}{W}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{W}{W}")));
        // When Defender of the Order is turned face up, creatures you control get +0/+2 until end of turn.
        this.addAbility(new TurnedFaceUpSourceTriggeredAbility(new BoostControlledEffect(0, 2, Duration.EndOfTurn)));
    }

    private DefenderOfTheOrder(final DefenderOfTheOrder card) {
        super(card);
    }

    @Override
    public DefenderOfTheOrder copy() {
        return new DefenderOfTheOrder(this);
    }
}
