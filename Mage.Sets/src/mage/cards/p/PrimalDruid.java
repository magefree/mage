
package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class PrimalDruid extends CardImpl {

    public PrimalDruid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // When Primal Druid dies, you may search your library for a basic land card, put it onto the battlefield tapped, then shuffle your library.
        Effect effect = new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true);
        effect.setText("you may search your library for a basic land card, put it onto the battlefield tapped, then shuffle");
        this.addAbility(new DiesSourceTriggeredAbility(effect, true));

    }

    private PrimalDruid(final PrimalDruid card) {
        super(card);
    }

    @Override
    public PrimalDruid copy() {
        return new PrimalDruid(this);
    }
}
