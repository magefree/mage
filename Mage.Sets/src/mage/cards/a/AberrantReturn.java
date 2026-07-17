package mage.cards.a;

import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldWithCounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AberrantReturn extends CardImpl {

    public AberrantReturn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}{B}");

        // Put one, two, or three target creature cards from graveyards onto the battlefield under your control. Each of them enters with an additional -1/-1 counter on it.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldWithCounterTargetEffect(CounterType.M1M1.createInstance())
                .setText("put one, two, or three target creature cards from graveyards onto the battlefield " +
                        "under your control. Each of them enters with an additional -1/-1 counter on it"));
        this.getSpellAbility().addTarget(new TargetCardInGraveyard(1, 3, StaticFilters.FILTER_CARD_CREATURES));
    }

    private AberrantReturn(final AberrantReturn card) {
        super(card);
    }

    @Override
    public AberrantReturn copy() {
        return new AberrantReturn(this);
    }
}
