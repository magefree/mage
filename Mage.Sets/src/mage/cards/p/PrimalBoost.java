
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.CycleTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class PrimalBoost extends CardImpl {

    public PrimalBoost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{G}");

        // Target creature gets +4/+4 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(4, 4, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        // Cycling {2}{G}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}{G}")));
        // When you cycle Primal Boost, you may have target creature get +1/+1 until end of turn.
        Ability ability = new CycleTriggeredAbility(new BoostTargetEffect(1, 1, Duration.EndOfTurn), true);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private PrimalBoost(final PrimalBoost card) {
        super(card);
    }

    @Override
    public PrimalBoost copy() {
        return new PrimalBoost(this);
    }
}
