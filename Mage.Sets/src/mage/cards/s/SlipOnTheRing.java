package mage.cards.s;

import java.util.UUID;

import mage.abilities.effects.common.ExileThenReturnTargetEffect;
import mage.abilities.effects.keyword.TheRingTemptsYouEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Susucr
 */
public final class SlipOnTheRing extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature you own");

    static {
        filter.add(TargetController.YOU.getOwnerPredicate());
    }

    public SlipOnTheRing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Exile target creature you own, then return it to the battlefield under your control.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
        this.getSpellAbility().addEffect(new ExileThenReturnTargetEffect(true, false));
        // The Ring tempts you.
        this.getSpellAbility().addEffect(new TheRingTemptsYouEffect());
    }

    private SlipOnTheRing(final SlipOnTheRing card) {
        super(card);
    }

    @Override
    public SlipOnTheRing copy() {
        return new SlipOnTheRing(this);
    }
}
