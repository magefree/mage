package mage.cards.f;

import mage.abilities.dynamicvalue.common.ControllerGainedLifeCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.PlayerGainedLifeWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FortifyingDraught extends CardImpl {

    public FortifyingDraught(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}");

        // You gain 2 life. Target creature gets +X/+X until end of turn, where X is the amount of life you gained this turn.
        this.getSpellAbility().addEffect(new GainLifeEffect(2));
        this.getSpellAbility().addEffect(new BoostTargetEffect(
                ControllerGainedLifeCount.instance,
                ControllerGainedLifeCount.instance,
                Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addWatcher(new PlayerGainedLifeWatcher());
        this.getSpellAbility().addHint(ControllerGainedLifeCount.getHint());
    }

    private FortifyingDraught(final FortifyingDraught card) {
        super(card);
    }

    @Override
    public FortifyingDraught copy() {
        return new FortifyingDraught(this);
    }
}
