

package mage.cards.m;

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
public final class MistyRainforest extends CardImpl {

    public MistyRainforest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},null);
        this.frameColor = new ObjectColor("UG");
        this.addAbility(new FetchLandActivatedAbility(SubType.FOREST, SubType.ISLAND));
    }

    private MistyRainforest(final MistyRainforest card) {
        super(card);
    }

    @Override
    public MistyRainforest copy() {
        return new MistyRainforest(this);
    }

}
