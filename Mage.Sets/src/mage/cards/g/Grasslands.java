
package mage.cards.g;

import java.util.EnumSet;
import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.FetchLandActivatedAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author LevelX2
 */
public final class Grasslands extends CardImpl {

    public Grasslands(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Grasslands enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // {tap}, Sacrifice Grasslands: Search your library for a Forest or Plains card and put it onto the battlefield. Then shuffle your library.
        this.addAbility(new FetchLandActivatedAbility(false, SubType.FOREST, SubType.PLAINS));
    }

    private Grasslands(final Grasslands card) {
        super(card);
    }

    @Override
    public Grasslands copy() {
        return new Grasslands(this);
    }
}
