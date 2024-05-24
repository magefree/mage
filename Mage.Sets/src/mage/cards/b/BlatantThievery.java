package mage.cards.b;

import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.EachOpponentPermanentTargetsAdjuster;
import mage.target.targetpointer.EachTargetPointer;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class BlatantThievery extends CardImpl {

    public BlatantThievery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{U}{U}{U}");

        // For each opponent, gain control of target permanent that player controls.
        this.getSpellAbility().addEffect(new GainControlTargetEffect(Duration.Custom, true)
                .setTargetPointer(new EachTargetPointer())
                .setText("for each opponent, gain control of target permanent that player controls"));
        this.getSpellAbility().setTargetAdjuster(new EachOpponentPermanentTargetsAdjuster());
        this.getSpellAbility().addTarget(new TargetPermanent());
    }

    private BlatantThievery(final BlatantThievery card) {
        super(card);
    }

    @Override
    public BlatantThievery copy() {
        return new BlatantThievery(this);
    }
}
