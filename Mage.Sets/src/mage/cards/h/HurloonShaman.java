
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.SacrificeAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledLandPermanent;

/**
 *
 * @author Derpthemeus
 */
public final class HurloonShaman extends CardImpl {

    public HurloonShaman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}{R}");
        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When Hurloon Shaman dies, each player sacrifices a land.
        this.addAbility(new DiesSourceTriggeredAbility(new SacrificeAllEffect(new FilterControlledLandPermanent("land"))));
    }

    private HurloonShaman(final HurloonShaman card) {
        super(card);
    }

    @Override
    public HurloonShaman copy() {
        return new HurloonShaman(this);
    }
}
