package mage.cards.p;

import mage.abilities.effects.common.counter.DistributeCountersEffect;
import mage.abilities.keyword.BeamMeUpAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanentAmount;

import java.util.UUID;

/**
 * @author muz
 */
public final class PureEnergy extends CardImpl {

    public PureEnergy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{W}");

        // Distribute two +1/+1 counters among one or two target creatures.
        this.getSpellAbility().addEffect(new DistributeCountersEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanentAmount(2));

        // Beam me up {2}{W}
        this.addAbility(new BeamMeUpAbility(this, "{2}{W}"));
    }

    private PureEnergy(final PureEnergy card) {
        super(card);
    }

    @Override
    public PureEnergy copy() {
        return new PureEnergy(this);
    }
}
