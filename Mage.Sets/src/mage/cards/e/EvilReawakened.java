package mage.cards.e;

import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldWithCounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EvilReawakened extends CardImpl {

    public EvilReawakened(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}");

        // Return target creature card from your graveyard to the battlefield with two additional +1/+1 counters on it.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldWithCounterTargetEffect(true, CounterType.P1P1.createInstance(2)));
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
    }

    private EvilReawakened(final EvilReawakened card) {
        super(card);
    }

    @Override
    public EvilReawakened copy() {
        return new EvilReawakened(this);
    }
}
