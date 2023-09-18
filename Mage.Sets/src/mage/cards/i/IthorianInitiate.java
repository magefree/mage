
package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.MeditateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 *
 * @author Styxo
 */
public final class IthorianInitiate extends CardImpl {

    public IthorianInitiate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.ITHORIAN);
        this.subtype.add(SubType.JEDI);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Ithorian Initiate enters the battlefield, you may search your library for a basic land, reveal it, and put it into your hand. If you do shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true), true));

        // Meditate {1}{G}
        this.addAbility(new MeditateAbility(new ManaCostsImpl<>("{1}{G}")));
    }

    private IthorianInitiate(final IthorianInitiate card) {
        super(card);
    }

    @Override
    public IthorianInitiate copy() {
        return new IthorianInitiate(this);
    }
}
