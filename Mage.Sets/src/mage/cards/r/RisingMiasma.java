
package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.AwakenAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author LevelX2
 */
public final class RisingMiasma extends CardImpl {

    public RisingMiasma(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{B}");

        // All creatures get -2/-2 until end of turn.
        this.getSpellAbility().addEffect(new BoostAllEffect(-2, -2, Duration.EndOfTurn));

        // Awaken 3 - {5}{B}{B}
        this.addAbility(new AwakenAbility(this, 3, "{5}{B}{B}"));
    }

    private RisingMiasma(final RisingMiasma card) {
        super(card);
    }

    @Override
    public RisingMiasma copy() {
        return new RisingMiasma(this);
    }
}
