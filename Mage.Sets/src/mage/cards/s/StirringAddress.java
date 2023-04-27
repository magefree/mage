package mage.cards.s;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
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
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 2, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());

        // Overload {5}{W}
        this.addAbility(new OverloadAbility(this, new BoostControlledEffect(
                2, 2, Duration.EndOfTurn
        ).setText("Each creature you control gets +2/+2 until end of turn."), new ManaCostsImpl<>("{5}{W}")));
    }

    private StirringAddress(final StirringAddress card) {
        super(card);
    }

    @Override
    public StirringAddress copy() {
        return new StirringAddress(this);
    }
}
