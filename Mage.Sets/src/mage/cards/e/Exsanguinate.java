package mage.cards.e;

import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.LoseLifeOpponentsYouGainLifeLostEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 *
 * @author Loki
 */
public final class Exsanguinate extends CardImpl {

    public Exsanguinate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{B}{B}");

        // Each opponent loses X life. You gain life equal to the life lost this way.
        this.getSpellAbility().addEffect(new LoseLifeOpponentsYouGainLifeLostEffect(ManacostVariableValue.REGULAR, "X life"));
    }

    private Exsanguinate(final Exsanguinate card) {
        super(card);
    }

    @Override
    public Exsanguinate copy() {
        return new Exsanguinate(this);
    }

}
