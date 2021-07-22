

package mage.cards.s;

import java.util.EnumSet;
import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.common.FetchLandActivatedAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class ScaldingTarn extends CardImpl {

    public ScaldingTarn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},null);
        this.frameColor = new ObjectColor("UR");
        this.addAbility(new FetchLandActivatedAbility(SubType.ISLAND, SubType.MOUNTAIN));
    }

    private ScaldingTarn(final ScaldingTarn card) {
        super(card);
    }

    @Override
    public ScaldingTarn copy() {
        return new ScaldingTarn(this);
    }

}

