
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
 * @author Backfir3
 */
public final class BloodVassal extends CardImpl {

    public BloodVassal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.THRULL);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Sacrifice Blood Vassal: Add {B}{B}.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.BlackMana(2), new SacrificeSourceCost()));
    }

    private BloodVassal(final BloodVassal card) {
        super(card);
    }

   @Override
    public BloodVassal copy() {
        return new BloodVassal(this);
    }
}
