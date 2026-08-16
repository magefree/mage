package mage.cards.b;

import java.util.UUID;

import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.constants.Duration;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetArtifactPermanent;
import mage.target.targetadjustment.ForEachPlayerTargetsAdjuster;
import mage.target.targetpointer.EachTargetPointer;

/**
 *
 * @author muz
 */
public final class BilbosBurglaring extends CardImpl {

    public BilbosBurglaring(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{U}{U}");

        // For each opponent, gain control of up to one target artifact that player controls.
        this.getSpellAbility().addEffect(new GainControlTargetEffect(Duration.Custom, true)
            .setTargetPointer(new EachTargetPointer())
            .setText("for each opponent, gain control of up to one target artifact that player controls"));
        this.getSpellAbility().addTarget(new TargetArtifactPermanent(0, 1));
        this.getSpellAbility().setTargetAdjuster(new ForEachPlayerTargetsAdjuster(false, true));
    }

    private BilbosBurglaring(final BilbosBurglaring card) {
        super(card);
    }

    @Override
    public BilbosBurglaring copy() {
        return new BilbosBurglaring(this);
    }
}
