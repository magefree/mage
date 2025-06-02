
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.FearAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author jeffwadsworth
 */
public final class Ratcatcher extends CardImpl {
    
    private static final FilterCard filter = new FilterCard("Rat card");
    
    static {
        filter.add(SubType.RAT.getPredicate());
    }

    public Ratcatcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}{B}");
        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Fear
        this.addAbility(FearAbility.getInstance());
        
        // At the beginning of your upkeep, you may search your library for a Rat card, reveal it, and put it into your hand. If you do, shuffle your library.
        TargetCardInLibrary targetCard = new TargetCardInLibrary(1, 1, filter);
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SearchLibraryPutInHandEffect(targetCard, true), true));
        
    }

    private Ratcatcher(final Ratcatcher card) {
        super(card);
    }

    @Override
    public Ratcatcher copy() {
        return new Ratcatcher(this);
    }
}
