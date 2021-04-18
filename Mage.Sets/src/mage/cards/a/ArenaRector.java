
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterPlaneswalkerCard;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author TheElk801
 */
public final class ArenaRector extends CardImpl {

    public ArenaRector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // When Arena Rector dies, you may exile it. If you do, search your library for a planeswalker card, put it onto the battlefield, then shuffle your library.
        this.addAbility(new DiesSourceTriggeredAbility(
                new DoIfCostPaid(
                        new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(new FilterPlaneswalkerCard())),
                        new ExileSourceFromGraveCost(),
                        "Exile to search for a planeswalker?"
                ).setText("you may exile it. If you do, search your library for a planeswalker card, put it onto the battlefield, then shuffle"), false
        ));
    }

    private ArenaRector(final ArenaRector card) {
        super(card);
    }

    @Override
    public ArenaRector copy() {
        return new ArenaRector(this);
    }
}
