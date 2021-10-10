
package mage.cards.t;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TimingRule;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class ThrillOfTheHunt extends CardImpl {

    public ThrillOfTheHunt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{G}");

        // Target creature gets +1/+2 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(1, 2, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        // Flashback {W}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl("{W}")));
    }

    private ThrillOfTheHunt(final ThrillOfTheHunt card) {
        super(card);
    }

    @Override
    public ThrillOfTheHunt copy() {
        return new ThrillOfTheHunt(this);
    }
}
