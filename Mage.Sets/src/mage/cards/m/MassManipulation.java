package mage.cards.m;

import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreatureOrPlaneswalker;
import mage.target.targetadjustment.XTargetsCountAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MassManipulation extends CardImpl {

    public MassManipulation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{X}{U}{U}{U}{U}");

        // Gain control of X target creatures and/or planeswalkers.
        this.getSpellAbility().addEffect(
                new GainControlTargetEffect(Duration.Custom, true)
                        .setText("Gain control of X target creatures and/or planeswalkers.")
        );
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
        this.getSpellAbility().setTargetAdjuster(new XTargetsCountAdjuster());
    }

    private MassManipulation(final MassManipulation card) {
        super(card);
    }

    @Override
    public MassManipulation copy() {
        return new MassManipulation(this);
    }
}
