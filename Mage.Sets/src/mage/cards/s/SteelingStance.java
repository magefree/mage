
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.ForecastAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class SteelingStance extends CardImpl {

    public SteelingStance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}{W}");


        // Creatures you control get +1/+1 until end of turn.
        this.getSpellAbility().addEffect(new BoostControlledEffect(1,1, Duration.EndOfTurn));
        // Forecast - {W}, Reveal Steeling Stance from your hand: Target creature gets +1/+1 until end of turn.
        Ability ability = new ForecastAbility(new BoostTargetEffect(1,1, Duration.EndOfTurn), new ManaCostsImpl<>("{W}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private SteelingStance(final SteelingStance card) {
        super(card);
    }

    @Override
    public SteelingStance copy() {
        return new SteelingStance(this);
    }
}
