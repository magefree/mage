
package mage.cards.d;

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
 * @author Temba21
 */
public final class DeathPulse extends CardImpl {

    public DeathPulse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{B}{B}");

        // Target creature gets -4/-4 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(-4, -4, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Cycling {1}{B}{B}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{1}{B}{B}")));
        // When you cycle Death Pulse, you may have target creature get -1/-1 until end of turn.
        Ability ability = new CycleTriggeredAbility(new BoostTargetEffect(-1, -1, Duration.EndOfTurn), true);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private DeathPulse(final DeathPulse card) {
        super(card);
    }

    @Override
    public DeathPulse copy() {
        return new DeathPulse(this);
    }
}
