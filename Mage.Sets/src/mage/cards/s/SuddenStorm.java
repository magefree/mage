
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class SuddenStorm extends CardImpl {

    public SuddenStorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{U}");


        // Tap up to two target creatures. Those creatures don't untap during their controllers' next untap steps. Scry 1.
        this.getSpellAbility().addEffect(new TapTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 2));
        this.getSpellAbility().addEffect(new DontUntapInControllersNextUntapStepTargetEffect("Those creatures"));
        this.getSpellAbility().addEffect(new ScryEffect(1));
    }

    private SuddenStorm(final SuddenStorm card) {
        super(card);
    }

    @Override
    public SuddenStorm copy() {
        return new SuddenStorm(this);
    }
}
