package mage.cards.m;

import java.util.UUID;

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
public final class MultiplyByZero extends CardImpl {

    public MultiplyByZero(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Target creature has base power and toughness 0/0 until end of turn.
        this.getSpellAbility().addEffect(new SetBasePowerToughnessTargetEffect(0, 0, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private MultiplyByZero(final MultiplyByZero card) {
        super(card);
    }

    @Override
    public MultiplyByZero copy() {
        return new MultiplyByZero(this);
    }
}
