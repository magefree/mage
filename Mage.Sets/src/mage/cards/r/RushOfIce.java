
package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.AwakenAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class RushOfIce extends CardImpl {

    public RushOfIce(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{U}");

        // Tap target creature. It doesn't untap during its controller's next untap step.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new TapTargetEffect());
        this.getSpellAbility().addEffect(new DontUntapInControllersNextUntapStepTargetEffect("It"));

        // Awaken 3—{4}{U}
        this.addAbility(new AwakenAbility(this, 3, "{4}{U}"));
    }

    private RushOfIce(final RushOfIce card) {
        super(card);
    }

    @Override
    public RushOfIce copy() {
        return new RushOfIce(this);
    }
}
