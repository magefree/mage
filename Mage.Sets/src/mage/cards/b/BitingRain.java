
package mage.cards.b;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.MadnessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author fireshoes
 */
public final class BitingRain extends CardImpl {

    public BitingRain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B}{B}");

        // All creatures get -2/-2 until end of turn.
        this.getSpellAbility().addEffect(new BoostAllEffect(-2, -2, Duration.EndOfTurn));

        // Madness {2}{B}
        this.addAbility(new MadnessAbility(new ManaCostsImpl<>("{2}{B}")));
    }

    private BitingRain(final BitingRain card) {
        super(card);
    }

    @Override
    public BitingRain copy() {
        return new BitingRain(this);
    }
}
