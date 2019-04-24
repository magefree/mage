
package mage.cards.p;

import java.util.UUID;
import mage.abilities.condition.common.CitysBlessingCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.keyword.AscendEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author LevelX2
 */
public final class PrideOfConquerors extends CardImpl {

    public PrideOfConquerors(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Ascend (If you control ten or more permanents, you get the city's blessing for the rest of the game.)
        this.getSpellAbility().addEffect(new AscendEffect());

        // Creatures you control get +1/+1 until end of turn. If you have the city's blessing, those creatures get +2/+2 until end of turn instead.
        this.getSpellAbility().addEffect(new ConditionalContinuousEffect(new BoostControlledEffect(2, 2, Duration.EndOfTurn),
                new BoostControlledEffect(1, 1, Duration.EndOfTurn), CitysBlessingCondition.instance,
                "Creatures you control get +1/+1 until end of turn. If you have the city's blessing, those creatures get +2/+2 until end of turn instead"));
    }

    public PrideOfConquerors(final PrideOfConquerors card) {
        super(card);
    }

    @Override
    public PrideOfConquerors copy() {
        return new PrideOfConquerors(this);
    }
}
