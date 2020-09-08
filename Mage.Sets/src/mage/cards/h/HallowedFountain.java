
package mage.cards.h;

import java.util.UUID;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.TapSourceUnlessPaysEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class HallowedFountain extends CardImpl {

    public HallowedFountain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, null);
        this.subtype.add(SubType.PLAINS);
        this.subtype.add(SubType.ISLAND);

        this.addAbility(new AsEntersBattlefieldAbility(new TapSourceUnlessPaysEffect(new PayLifeCost(2)), "you may pay 2 life. If you don't, it enters the battlefield tapped"));
        this.addAbility(new WhiteManaAbility());
        this.addAbility(new BlueManaAbility());
    }

    public HallowedFountain(final HallowedFountain card) {
        super(card);
    }

    @Override
    public HallowedFountain copy() {
        return new HallowedFountain(this);
    }

}
