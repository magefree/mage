
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesBlockedSourceTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author LoneFox
 */
public final class TrainedCheetah extends CardImpl {

    public TrainedCheetah(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.CAT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Trained Cheetah becomes blocked, it gets +1/+1 until end of turn.
        this.addAbility(new BecomesBlockedSourceTriggeredAbility(new BoostSourceEffect(1, 1, Duration.EndOfTurn), false));
    }

    private TrainedCheetah(final TrainedCheetah card) {
        super(card);
    }

    @Override
    public TrainedCheetah copy() {
        return new TrainedCheetah(this);
    }
}
