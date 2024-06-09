package mage.cards.d;

import mage.abilities.effects.common.continuous.SetBasePowerToughnessTargetEffect;
import mage.abilities.effects.keyword.TheRingTemptsYouEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DreadfulAsTheStorm extends CardImpl {

    public DreadfulAsTheStorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Target creature has base power and toughness 5/5 until end of turn. The Ring tempts you.
        this.getSpellAbility().addEffect(new SetBasePowerToughnessTargetEffect(5, 5, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new TheRingTemptsYouEffect());
    }

    private DreadfulAsTheStorm(final DreadfulAsTheStorm card) {
        super(card);
    }

    @Override
    public DreadfulAsTheStorm copy() {
        return new DreadfulAsTheStorm(this);
    }
}
