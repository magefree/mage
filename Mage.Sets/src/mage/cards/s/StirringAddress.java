package mage.cards.s;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.OverloadAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StirringAddress extends CardImpl {

    public StirringAddress(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Target creature you control gets +2/+2 until end of turn.
        // Overload {5}{W}
        OverloadAbility.implementOverloadAbility(this, new ManaCostsImpl<>("{5}{W}"), new TargetControlledCreaturePermanent(),
                new BoostTargetEffect(2, 2, Duration.EndOfTurn));
    }

    private StirringAddress(final StirringAddress card) {
        super(card);
    }

    @Override
    public StirringAddress copy() {
        return new StirringAddress(this);
    }
}
