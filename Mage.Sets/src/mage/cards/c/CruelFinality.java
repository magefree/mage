
package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class CruelFinality extends CardImpl {

    public CruelFinality(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}");

        // Target creature gets -2/-2 until end of turn. Scry 1.
        getSpellAbility().addEffect(new BoostTargetEffect(-2, -2, Duration.EndOfTurn));
        getSpellAbility().addTarget(new TargetCreaturePermanent());
        getSpellAbility().addEffect(new ScryEffect(1));
    }

    private CruelFinality(final CruelFinality card) {
        super(card);
    }

    @Override
    public CruelFinality copy() {
        return new CruelFinality(this);
    }
}
