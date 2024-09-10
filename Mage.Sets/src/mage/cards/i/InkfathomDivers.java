

package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.LookLibraryControllerEffect;
import mage.abilities.keyword.IslandwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX
 */
public final class InkfathomDivers extends CardImpl {

    public InkfathomDivers (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Islandwalk
        this.addAbility(new IslandwalkAbility());

        // When Inkfathom Divers enters the battlefield, look at the top four cards of your library, then put them back in any order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LookLibraryControllerEffect(4)));

    }

    private InkfathomDivers(final InkfathomDivers card) {
        super(card);
    }

    @Override
    public InkfathomDivers copy() {
        return new InkfathomDivers(this);
    }

}

