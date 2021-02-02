
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author Loki
 */
public final class BloodPet extends CardImpl {

    public BloodPet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}");
        this.subtype.add(SubType.THRULL);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Sacrifice Blood Pet: Add {B}.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.BlackMana(1), new SacrificeSourceCost()));
    }

    private BloodPet(final BloodPet card) {
        super(card);
    }

    @Override
    public BloodPet copy() {
        return new BloodPet(this);
    }
}
