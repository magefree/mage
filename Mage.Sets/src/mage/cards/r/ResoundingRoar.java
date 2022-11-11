
package mage.cards.r;

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
 * @author Plopman
 */
public final class ResoundingRoar extends CardImpl {

    public ResoundingRoar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{G}");


        // Target creature gets +3/+3 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(3, 3, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        // Cycling {5}{R}{G}{W}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{5}{R}{G}{W}")));
        // When you cycle Resounding Roar, target creature gets +6/+6 until end of turn.
        Ability ability = new CycleTriggeredAbility(new BoostTargetEffect(6, 6, Duration.EndOfTurn));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private ResoundingRoar(final ResoundingRoar card) {
        super(card);
    }

    @Override
    public ResoundingRoar copy() {
        return new ResoundingRoar(this);
    }
}
