

package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.OpponentControlsMoreCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterBySubtypeCard;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class KnightOfTheWhiteOrchid extends CardImpl {

    public KnightOfTheWhiteOrchid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());
        
        // When Knight of the White Orchid enters the battlefield, if an opponent controls more lands than you, you may search your library for a Plains card, put it onto the battlefield, then shuffle your library.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(0, 1, new FilterBySubtypeCard(SubType.PLAINS)), false), true),
                new OpponentControlsMoreCondition(StaticFilters.FILTER_LANDS),
                "When {this} enters the battlefield, if an opponent controls more lands than you, you may search your library for a Plains card, put it onto the battlefield, then shuffle."));
        
    }

    private KnightOfTheWhiteOrchid(final KnightOfTheWhiteOrchid card) {
        super(card);
    }

    @Override
    public KnightOfTheWhiteOrchid copy() {
        return new KnightOfTheWhiteOrchid(this);
    }

}
