package mage.cards.f;

import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayTargetPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInLibrary;
import mage.target.targetpointer.SecondTargetPointer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FertilidsFavor extends CardImpl {

    public FertilidsFavor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{G}");

        // Target player searches their library for a basic land card, puts it onto the battlefield tapped, then shuffles. Put two +1/+1 counters on up to one target artifact or creature.
        this.getSpellAbility().addEffect(new SearchLibraryPutInPlayTargetPlayerEffect(
                new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true
        ));
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance(2))
                .setText("put two +1/+1 counters on up to one target artifact or creature")
                .setTargetPointer(new SecondTargetPointer()));
        this.getSpellAbility().addTarget(new TargetPermanent(
                0, 1, StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE
        ));
    }

    private FertilidsFavor(final FertilidsFavor card) {
        super(card);
    }

    @Override
    public FertilidsFavor copy() {
        return new FertilidsFavor(this);
    }
}
