
package mage.cards.h;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class HowlingFury extends CardImpl {

    public HowlingFury(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B}");

        // Target creature gets +4/+0 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(4, 0, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private HowlingFury(final HowlingFury card) {
        super(card);
    }

    @Override
    public HowlingFury copy() {
        return new HowlingFury(this);
    }
}
