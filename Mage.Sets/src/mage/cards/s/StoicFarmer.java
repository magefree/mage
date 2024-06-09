package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.OpponentControlsMoreCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.ForetellAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StoicFarmer extends CardImpl {

    private static final FilterCard filter = new FilterCard("a basic Plains card");

    static {
        filter.add(SuperType.BASIC.getPredicate());
        filter.add(SubType.PLAINS.getPredicate());
    }

    private static final Condition condition = new OpponentControlsMoreCondition(StaticFilters.FILTER_LANDS);

    public StoicFarmer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.PEASANT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Stoic Farmer enters the battlefield, search your library for a basic Plains card and reveal it. If an opponent controls more lands than you, put it onto the battlefield tapped. Otherwise, put it into your hand. Then shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new ConditionalOneShotEffect(
                        new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter), true),
                        new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true),
                        condition, "search your library for a basic Plains card and reveal it. " +
                        "If an opponent controls more lands than you, put it onto the battlefield tapped. " +
                        "Otherwise put it into your hand. Then shuffle"
                )
        ));

        // Foretell {1}{W}
        this.addAbility(new ForetellAbility(this, "{1}{W}"));
    }

    private StoicFarmer(final StoicFarmer card) {
        super(card);
    }

    @Override
    public StoicFarmer copy() {
        return new StoicFarmer(this);
    }
}
