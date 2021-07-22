
package mage.cards.m;

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
public final class MountainValley extends CardImpl {

    public MountainValley(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Mountain Valley enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // {tap}, Sacrifice Mountain Valley: Search your library for a Mountain or Forest card and put it onto the battlefield. Then shuffle your library.
        this.addAbility(new FetchLandActivatedAbility(false, SubType.MOUNTAIN, SubType.FOREST));

    }

    private MountainValley(final MountainValley card) {
        super(card);
    }

    @Override
    public MountainValley copy() {
        return new MountainValley(this);
    }
}
