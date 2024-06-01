package mage.cards.s;

import mage.abilities.Mode;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.SplitSecondAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class SiegeSmash extends CardImpl {

    public SiegeSmash(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Split second
        this.addAbility(new SplitSecondAbility());

        // Choose one --
        // * Destroy target artifact.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetArtifactPermanent());

        // * Target creature gets +3/+2 and gains trample until end of turn.
        Mode mode = new Mode(
                new BoostTargetEffect(3, 2, Duration.EndOfTurn)
                        .setText("Target creature gets +3/+3")
        );
        mode.addEffect(
                new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn)
                        .setText("and gains trample until end of turn")
        );
        mode.addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addMode(mode);
    }

    private SiegeSmash(final SiegeSmash card) {
        super(card);
    }

    @Override
    public SiegeSmash copy() {
        return new SiegeSmash(this);
    }
}
