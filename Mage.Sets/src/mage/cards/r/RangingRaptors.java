
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author TheElk801
 */
public final class RangingRaptors extends CardImpl {

    public RangingRaptors(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Enrage - Whenever Ranging Raptors is dealt damage, you may search your library for a basic land card, put it onto the battlefield, then shuffle your library.
        Ability ability = new DealtDamageToSourceTriggeredAbility(
                new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true),
                true,
                true);
        this.addAbility(ability);
    }

    public RangingRaptors(final RangingRaptors card) {
        super(card);
    }

    @Override
    public RangingRaptors copy() {
        return new RangingRaptors(this);
    }
}
