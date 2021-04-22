
package mage.cards.r;

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
public final class RockyTarPit extends CardImpl {

    public RockyTarPit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Rocky Tar Pit enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // {tap}, Sacrifice Rocky Tar Pit: Search your library for a Swamp or Mountain card and put it onto the battlefield. Then shuffle your library.
        this.addAbility(new FetchLandActivatedAbility(false, SubType.SWAMP, SubType.MOUNTAIN));
    }

    private RockyTarPit(final RockyTarPit card) {
        super(card);
    }

    @Override
    public RockyTarPit copy() {
        return new RockyTarPit(this);
    }
}
