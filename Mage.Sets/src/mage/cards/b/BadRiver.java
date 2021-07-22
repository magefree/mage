
package mage.cards.b;

import java.util.EnumSet;
import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.FetchLandActivatedAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class BadRiver extends CardImpl {

    public BadRiver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Bad River enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // {tap}, Sacrifice Bad River: Search your library for an Island or Swamp card and put it onto the battlefield. Then shuffle your library.
        this.addAbility(new FetchLandActivatedAbility(false, SubType.ISLAND, SubType.SWAMP));

    }

    private BadRiver(final BadRiver card) {
        super(card);
    }

    @Override
    public BadRiver copy() {
        return new BadRiver(this);
    }
}
