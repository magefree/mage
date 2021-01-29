package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheWorldTree extends CardImpl {

    private static final FilterCard filter = new FilterCard("God cards");
    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND, ComparisonType.MORE_THAN, 5
    );

    static {
        filter.add(SubType.GOD.getPredicate());
    }

    public TheWorldTree(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // The World Tree enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {G}.
        this.addAbility(new GreenManaAbility());

        // As long as you control six or more lands, lands you control have "{T}: Add one mana of any color."
        this.addAbility(new SimpleStaticAbility(
                new ConditionalContinuousEffect(
                        new GainAbilityControlledEffect(
                                new AnyColorManaAbility(), Duration.WhileOnBattlefield,
                                StaticFilters.FILTER_LANDS, false
                        ), condition, "as long as you control six or more lands, " +
                        "lands you control have \"{T}: Add one mana of any color.\""
                )
        ));

        // {W}{W}{U}{U}{B}{B}{R}{R}{G}{G}, {T}, Sacrifice The World Tree: Search your library for any number of God cards, put them onto the battlefield tapped, then shuffle your library.
        Ability ability = new SimpleActivatedAbility(new SearchLibraryPutInPlayEffect(
                new TargetCardInLibrary(0, Integer.MAX_VALUE, filter)
        ), new ManaCostsImpl<>("{W}{W}{U}{U}{B}{B}{R}{R}{G}{G}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private TheWorldTree(final TheWorldTree card) {
        super(card);
    }

    @Override
    public TheWorldTree copy() {
        return new TheWorldTree(this);
    }
}
