package mage.cards.d;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.LoseLifeOpponentsYouGainLifeLostEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class DebtToTheDeathless extends CardImpl {

    private static final DynamicValue xValue = new MultipliedValue(ManacostVariableValue.REGULAR, 2);

    public DebtToTheDeathless(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{W}{W}{B}{B}");

        // Each opponent loses two times X life. You gain life equal to the life lost this way.
        this.getSpellAbility().addEffect(new LoseLifeOpponentsYouGainLifeLostEffect(xValue, "two times X life"));
    }

    private DebtToTheDeathless(final DebtToTheDeathless card) {
        super(card);
    }

    @Override
    public DebtToTheDeathless copy() {
        return new DebtToTheDeathless(this);
    }
}
