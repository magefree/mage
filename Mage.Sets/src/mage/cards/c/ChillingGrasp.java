
package mage.cards.c;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.MadnessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class ChillingGrasp extends CardImpl {

    public ChillingGrasp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{U}");

        // Tap up to two target creatures. Those creatures don't untap during their controller's next uptap step.
        this.getSpellAbility().addEffect(new TapTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 2));
        this.getSpellAbility().addEffect(new DontUntapInControllersNextUntapStepTargetEffect("Those creatures"));

        // Madness {3}{U}
        this.addAbility(new MadnessAbility(new ManaCostsImpl<>("{3}{U}")));
    }

    private ChillingGrasp(final ChillingGrasp card) {
        super(card);
    }

    @Override
    public ChillingGrasp copy() {
        return new ChillingGrasp(this);
    }
}
