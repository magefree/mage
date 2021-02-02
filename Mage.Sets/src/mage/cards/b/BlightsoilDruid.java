
package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 *
 * @author Loki
 */
public final class BlightsoilDruid extends CardImpl {

    public BlightsoilDruid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.ELF, SubType.DRUID);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);
        Ability ability = new GreenManaAbility();
        ability.addCost(new PayLifeCost(1));
        this.addAbility(ability);
    }

    private BlightsoilDruid(final BlightsoilDruid card) {
        super(card);
    }

    @Override
    public BlightsoilDruid copy() {
        return new BlightsoilDruid(this);
    }
}
