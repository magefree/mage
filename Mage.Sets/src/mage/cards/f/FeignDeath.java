package mage.cards.f;

import java.util.UUID;

import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldWithCounterEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author weirddan455
 */
public final class FeignDeath extends CardImpl {

    public FeignDeath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}");

        // Until end of turn, target creature gains "When this creature dies, return it to the battlefield tapped under its owner's control with a +1/+1 counter on it."
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                new DiesSourceTriggeredAbility(new ReturnSourceFromGraveyardToBattlefieldWithCounterEffect(
                        CounterType.P1P1.createInstance(), true, true, false, false)),
                Duration.EndOfTurn,
                "Until end of turn, target creature gains \"When this creature dies, return it to the battlefield tapped under its owner's control with a +1/+1 counter on it.\""
        ));
    }

    private FeignDeath(final FeignDeath card) {
        super(card);
    }

    @Override
    public FeignDeath copy() {
        return new FeignDeath(this);
    }
}
