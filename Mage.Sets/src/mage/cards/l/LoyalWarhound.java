package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.OpponentControlsMoreCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.constants.SubType;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author weirddan455
 */
public final class LoyalWarhound extends CardImpl {

    private static final FilterCard filter = new FilterCard("basic Plains card");

    static {
        filter.add(SuperType.BASIC.getPredicate());
        filter.add(SubType.PLAINS.getPredicate());
    }

    public LoyalWarhound(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.DOG);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // When Loyal Warhound enters the battlefield, if an opponent controls more lands than you,
        // search your library for a basic Plains card, put it onto the battlefield tapped, then shuffle.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter), true)),
                new OpponentControlsMoreCondition(StaticFilters.FILTER_LANDS),
                "When {this} enters the battlefield, if an opponent controls more lands than you, "
                + "search your library for a basic Plains card, put it onto the battlefield tapped, then shuffle."
        ));
    }

    private LoyalWarhound(final LoyalWarhound card) {
        super(card);
    }

    @Override
    public LoyalWarhound copy() {
        return new LoyalWarhound(this);
    }
}
