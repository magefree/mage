package mage.cards.s;

import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.keyword.TheRingTemptsYouEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SoothingOfSmeagol extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("nontoken creature");

    static {
        filter.add(TokenPredicate.FALSE);
    }

    public SoothingOfSmeagol(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Return target nontoken creature to its owner's hand. The Ring tempts you.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addEffect(new TheRingTemptsYouEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private SoothingOfSmeagol(final SoothingOfSmeagol card) {
        super(card);
    }

    @Override
    public SoothingOfSmeagol copy() {
        return new SoothingOfSmeagol(this);
    }
}
