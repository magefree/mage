package mage.cards.s;

import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetLandPermanent;
import mage.target.targetpointer.SecondTargetPointer;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SeismicShift extends CardImpl {

    public SeismicShift(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}");

        // Destroy target land. Up to two target creatures can't block this turn.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetLandPermanent());
        this.getSpellAbility().addEffect(new CantBlockTargetEffect(Duration.EndOfTurn)
                .setText("Up to two target creatures can't block this turn")
                .setTargetPointer(new SecondTargetPointer()));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 2));
    }

    private SeismicShift(final SeismicShift card) {
        super(card);
    }

    @Override
    public SeismicShift copy() {
        return new SeismicShift(this);
    }
}
