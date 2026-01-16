package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCardInLibrary;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;

/**
 *
 * @author muz
 */
public final class MaximusKnightApparent extends CardImpl {

    private static final FilterCard filter = new FilterCard(SubType.EQUIPMENT, "an Equipment card with mana value 2");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, 2));
    }

    public MaximusKnightApparent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Maximus enters, you may search your library for an Equipment card with mana value 2, reveal it, put it into your hand, then shuffle.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
            new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true), true
        ));

        // {1}, Sacrifice an artifact: You get {E}{E} (two energy counters).
        Ability ability = new SimpleActivatedAbility(
            new GetEnergyCountersControllerEffect(2),
            new GenericManaCost(1)
        );
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT_AN));
        this.addAbility(ability);
    }

    private MaximusKnightApparent(final MaximusKnightApparent card) {
        super(card);
    }

    @Override
    public MaximusKnightApparent copy() {
        return new MaximusKnightApparent(this);
    }
}
