
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.MayTapOrUntapTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class RetreatToCoralhelm extends CardImpl {

    public RetreatToCoralhelm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{U}");

        // <i>Landfall</i>- Whenever a land enters the battlefield under your control, choose one - You may tap or untap target creature; or Scry 1.
        LandfallAbility ability = new LandfallAbility(new MayTapOrUntapTargetEffect(), false);
        ability.addTarget(new TargetCreaturePermanent());
        Mode mode = new Mode(new ScryEffect(1));
        ability.addMode(mode);
        this.addAbility(ability);
    }

    private RetreatToCoralhelm(final RetreatToCoralhelm card) {
        super(card);
    }

    @Override
    public RetreatToCoralhelm copy() {
        return new RetreatToCoralhelm(this);
    }
}
