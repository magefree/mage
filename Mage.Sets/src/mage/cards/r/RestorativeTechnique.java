package mage.cards.r;

import mage.abilities.effects.common.GainLifeTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayTargetPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.SecondTargetPointer;

import java.util.UUID;

/**
 *
 * @author muz
 */
public final class RestorativeTechnique extends CardImpl {

    public RestorativeTechnique(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Target player gains 2 life, then searches their library for a basic land card, puts it onto the battlefield tapped, then shuffles. Put a +1/+1 counter on up to one target creature.
        this.getSpellAbility().addEffect(new GainLifeTargetEffect(2));
        this.getSpellAbility().addEffect(new SearchLibraryPutInPlayTargetPlayerEffect(
            new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true
        ).setText("then searches their library for a basic land card, puts it onto the battlefield tapped, then shuffles"));
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance())
            .setTargetPointer(new SecondTargetPointer()));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 1));
    }

    private RestorativeTechnique(final RestorativeTechnique card) {
        super(card);
    }

    @Override
    public RestorativeTechnique copy() {
        return new RestorativeTechnique(this);
    }
}
