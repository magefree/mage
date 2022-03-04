
package mage.cards.t;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterLandPermanent;
import mage.target.common.TargetCardInLibrary;

/**
 * @author TheElk801
 */
public final class ThaumaticCompass extends CardImpl {

    public ThaumaticCompass(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");
        this.secondSideCardClazz = mage.cards.s.SpiresOfOrazca.class;

        // {3}, {T}: Search your library for a basic land card, reveal it, put it into your hand, then shuffle your library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true),
                new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // At the beginning of your end step, if you control seven or more lands, transform Thaumatic Compass.
        this.addAbility(new TransformAbility());
        TriggeredAbility ability2 = new BeginningOfEndStepTriggeredAbility(new TransformSourceEffect(), TargetController.YOU, false);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                ability2,
                new PermanentsOnTheBattlefieldCondition(new FilterLandPermanent(), ComparisonType.MORE_THAN, 6, true),
                "At the beginning of your end step, if you control seven or more lands, transform {this}."));
    }

    private ThaumaticCompass(final ThaumaticCompass card) {
        super(card);
    }

    @Override
    public ThaumaticCompass copy() {
        return new ThaumaticCompass(this);
    }
}
