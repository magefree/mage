package mage.cards.a;

import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageWithPowerFromOneToAnotherTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author ciaccona007
 */
public final class Ambuscade extends CardImpl {

    public Ambuscade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");

        // Target creature you control gets +1/+0 until end of turn.
        Effect effect = new BoostTargetEffect(1, 0, Duration.EndOfTurn);
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());

        // It deals damage equal to its power to target creature you don't control.
        this.getSpellAbility().addEffect(new DamageWithPowerFromOneToAnotherTargetEffect("It"));
        this.getSpellAbility().addTarget(new TargetOpponentsCreaturePermanent()); // second target for effect
    }

    private Ambuscade(final Ambuscade card) {
        super(card);
    }

    @Override
    public Ambuscade copy() {
        return new Ambuscade(this);
    }
}
