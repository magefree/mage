
package mage.cards.e;

import java.util.UUID;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.AwakenAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public final class EarthenArms extends CardImpl {

    public EarthenArms(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{G}");

        // Put two +1/+1 counters on target permanent.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance(2)));
        this.getSpellAbility().addTarget(new TargetPermanent());

        // Awaken 4 - {6}{G}
        this.addAbility(new AwakenAbility(this, 4, "{6}{G}"));
    }

    private EarthenArms(final EarthenArms card) {
        super(card);
    }

    @Override
    public EarthenArms copy() {
        return new EarthenArms(this);
    }
}
