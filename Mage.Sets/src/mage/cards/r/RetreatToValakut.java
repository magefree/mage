
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
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
public final class RetreatToValakut extends CardImpl {

    public RetreatToValakut(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{R}");

        // <i>Landfall</i>- Whenever a land enters the battlefield under your control, choose one - Target creature gets +2/+0 until end of turn;
        LandfallAbility ability = new LandfallAbility(new BoostTargetEffect(2, 0, Duration.EndOfTurn), false);
        ability.addTarget(new TargetCreaturePermanent());

        // or Target creature can't block this turn.
        Mode mode = new Mode(new CantBlockTargetEffect(Duration.EndOfTurn));
        mode.addTarget(new TargetCreaturePermanent());
        ability.addMode(mode);
        this.addAbility(ability);
    }

    private RetreatToValakut(final RetreatToValakut card) {
        super(card);
    }

    @Override
    public RetreatToValakut copy() {
        return new RetreatToValakut(this);
    }
}
