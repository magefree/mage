
package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author Quercitron
 */
public final class DrownInSorrow extends CardImpl {

    public DrownInSorrow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{B}{B}");


        // All creatures get -2/-2 until end of turn. Scry 1.
        this.getSpellAbility().addEffect(new BoostAllEffect(-2, -2, Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new ScryEffect(1));
    }

    private DrownInSorrow(final DrownInSorrow card) {
        super(card);
    }

    @Override
    public DrownInSorrow copy() {
        return new DrownInSorrow(this);
    }
}
