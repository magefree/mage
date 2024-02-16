
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CycleTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public final class KrosanTusker extends CardImpl {

    public KrosanTusker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{G}{G}");
        this.subtype.add(SubType.BOAR);
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Cycling {2}{G}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}{G}")));
        // When you cycle Krosan Tusker, you may search your library for a basic land card, reveal that card, put it into your hand, then shuffle your library.
        this.addAbility(new CycleTriggeredAbility(
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true, true),
                true));
    }

    private KrosanTusker(final KrosanTusker card) {
        super(card);
    }

    @Override
    public KrosanTusker copy() {
        return new KrosanTusker(this);
    }
}
