
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.costs.mana.ManaCostsImpl;
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
 * @author L_J
 */
public final class JohnnyComboPlayer extends CardImpl {

    public JohnnyComboPlayer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.GAMER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        
        // {4}: Search your library for a card, put that card into your hand, then shuffle your library.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new SearchLibraryPutInHandEffect(new TargetCardInLibrary(new FilterCard("a card")), false), new ManaCostsImpl<>("{4}")));
    }

    private JohnnyComboPlayer(final JohnnyComboPlayer card) {
        super(card);
    }

    @Override
    public JohnnyComboPlayer copy() {
        return new JohnnyComboPlayer(this);
    }
}
