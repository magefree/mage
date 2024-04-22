
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public final class CaptainSisay extends CardImpl {

    private static final FilterCard filter = new FilterCard("legendary card");
    
    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }
    
    public CaptainSisay(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {tap}: Search your library for a legendary card, reveal that card, and put it into your hand. Then shuffle your library.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true, true), new TapSourceCost()));
    }

    private CaptainSisay(final CaptainSisay card) {
        super(card);
    }

    @Override
    public CaptainSisay copy() {
        return new CaptainSisay(this);
    }
}
