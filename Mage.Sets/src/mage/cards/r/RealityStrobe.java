package mage.cards.r;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileSpellWithTimeCountersEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPermanent;

/**
 *
 * @author fireshoes
 */
public final class RealityStrobe extends CardImpl {

    public RealityStrobe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{U}{U}");

        // Return target permanent to its owner's hand. Exile Reality Strobe with three time counters on it.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent());
        this.getSpellAbility().addEffect(new ExileSpellWithTimeCountersEffect(3));
        
        // Suspend 3—{2}{U}
        this.addAbility(new SuspendAbility(3, new ManaCostsImpl<>("{2}{U}"), this));
    }

    private RealityStrobe(final RealityStrobe card) {
        super(card);
    }

    @Override
    public RealityStrobe copy() {
        return new RealityStrobe(this);
    }
}
