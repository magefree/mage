package mage.cards.c;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CoordinatedCharge extends CardImpl {

    public CoordinatedCharge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{W}");

        // Creatures you control get +2/+1 until end of turn.
        this.getSpellAbility().addEffect(new BoostControlledEffect(2, 1, Duration.EndOfTurn));

        // Cycling {2}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private CoordinatedCharge(final CoordinatedCharge card) {
        super(card);
    }

    @Override
    public CoordinatedCharge copy() {
        return new CoordinatedCharge(this);
    }
}
