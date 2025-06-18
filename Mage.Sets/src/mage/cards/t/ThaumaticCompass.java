package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.hint.common.LandsYouControlHint;
import mage.abilities.keyword.TransformAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterLandPermanent;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThaumaticCompass extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterLandPermanent("you control seven or more lands"),
            ComparisonType.MORE_THAN, 6, true
    );

    public ThaumaticCompass(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");
        this.secondSideCardClazz = mage.cards.s.SpiresOfOrazca.class;

        // {3}, {T}: Search your library for a basic land card, reveal it, put it into your hand, then shuffle your library.
        Ability ability = new SimpleActivatedAbility(
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true),
                new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // At the beginning of your end step, if you control seven or more lands, transform Thaumatic Compass.
        this.addAbility(new TransformAbility());
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new TransformSourceEffect())
                .withInterveningIf(condition).addHint(LandsYouControlHint.instance));
    }

    private ThaumaticCompass(final ThaumaticCompass card) {
        super(card);
    }

    @Override
    public ThaumaticCompass copy() {
        return new ThaumaticCompass(this);
    }
}
