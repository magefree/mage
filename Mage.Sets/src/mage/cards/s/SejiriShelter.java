package mage.cards.s;

import mage.abilities.effects.common.continuous.GainProtectionFromColorTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SejiriShelter extends CardImpl {

    public SejiriShelter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        this.modalDFC = true;
        this.secondSideCardClazz = mage.cards.s.SejiriGlacier.class;

        // Target creature you control gains protection from the color of your choice until end of turn.
        this.getSpellAbility().addEffect(new GainProtectionFromColorTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
    }

    private SejiriShelter(final SejiriShelter card) {
        super(card);
    }

    @Override
    public SejiriShelter copy() {
        return new SejiriShelter(this);
    }
}
