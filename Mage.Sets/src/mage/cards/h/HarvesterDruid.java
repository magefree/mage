
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.mana.AnyColorLandsProduceManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

/**
 *
 * @author LoneFox
 */
public final class HarvesterDruid extends CardImpl {

    public HarvesterDruid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Add one mana of any color that a land you control could produce.
        this.addAbility(new AnyColorLandsProduceManaAbility(TargetController.YOU));
    }

    private HarvesterDruid(final HarvesterDruid card) {
        super(card);
    }

    @Override
    public HarvesterDruid copy() {
        return new HarvesterDruid(this);
    }
}
