package mage.cards.a;

import java.util.UUID;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.keyword.EmpowerJaceEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author muz
 */
public final class AcademicAscent extends CardImpl {

    public AcademicAscent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Target creature gets +2/+2 and gains flying until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 2, Duration.EndOfTurn).setText("Target creature gets +2/+2"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn).setText("and gains flying until end of turn"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Empower Jace 2.
        this.getSpellAbility().addEffect(new EmpowerJaceEffect(2).concatBy("<br>"));
    }

    private AcademicAscent(final AcademicAscent card) {
        super(card);
    }

    @Override
    public AcademicAscent copy() {
        return new AcademicAscent(this);
    }
}
