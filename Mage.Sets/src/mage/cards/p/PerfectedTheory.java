package mage.cards.p;

import java.util.UUID;

import mage.abilities.Mode;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author muz
 */
public final class PerfectedTheory extends CardImpl {

    public PerfectedTheory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // Choose one --
        // * Target creature has base power and toughness 1/1 until end of turn.
        this.getSpellAbility().addEffect(new SetBasePowerToughnessTargetEffect(1, 1, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // * Target creature has base power and toughness 4/5 until end of turn.
        this.getSpellAbility().addMode(
            new Mode(new SetBasePowerToughnessTargetEffect(4, 5, Duration.EndOfTurn))
                .addTarget(new TargetCreaturePermanent())
        );
    }

    private PerfectedTheory(final PerfectedTheory card) {
        super(card);
    }

    @Override
    public PerfectedTheory copy() {
        return new PerfectedTheory(this);
    }
}
