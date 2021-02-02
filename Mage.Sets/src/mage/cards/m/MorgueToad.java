
package mage.cards.m;

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
 * @author FenrisulfrX
 */
public final class MorgueToad extends CardImpl {

    public MorgueToad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.FROG);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Sacrifice Morgue Toad: Add {U}{R}.
        SimpleManaAbility ability = new SimpleManaAbility(Zone.BATTLEFIELD, new Mana(0, 1, 0, 1, 0, 0, 0, 0), new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private MorgueToad(final MorgueToad card) {
        super(card);
    }

    @Override
    public MorgueToad copy() {
        return new MorgueToad(this);
    }
}
