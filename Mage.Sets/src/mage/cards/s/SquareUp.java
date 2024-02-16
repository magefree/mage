package mage.cards.s;

import mage.abilities.effects.common.continuous.SetBasePowerToughnessTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SquareUp extends CardImpl {

    public SquareUp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G/U}");

        // Target creature has base power and toughness 4/4 until end of turn.
        this.getSpellAbility().addEffect(new SetBasePowerToughnessTargetEffect(4, 4, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private SquareUp(final SquareUp card) {
        super(card);
    }

    @Override
    public SquareUp copy() {
        return new SquareUp(this);
    }
}
