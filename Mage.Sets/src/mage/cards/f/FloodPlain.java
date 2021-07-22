
package mage.cards.f;

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
public final class FloodPlain extends CardImpl {

    public FloodPlain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Flood Plain enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // {tap}, Sacrifice Flood Plain: Search your library for a Plains or Island card and put it onto the battlefield. Then shuffle your library.
        this.addAbility(new FetchLandActivatedAbility(false, SubType.PLAINS, SubType.ISLAND));
    }

    private FloodPlain(final FloodPlain card) {
        super(card);
    }

    @Override
    public FloodPlain copy() {
        return new FloodPlain(this);
    }
}
