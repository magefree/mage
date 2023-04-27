
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author Plopman
 */
public final class DarigaazsAttendant extends CardImpl {

    public DarigaazsAttendant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{5}");
        this.subtype.add(SubType.GOLEM);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {1}, Sacrifice Darigaaz's Attendant: Add {B}{R}{G}.
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, new Mana(0, 0, 1, 1, 1, 0, 0, 0), new ManaCostsImpl<>("{1}"));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private DarigaazsAttendant(final DarigaazsAttendant card) {
        super(card);
    }

    @Override
    public DarigaazsAttendant copy() {
        return new DarigaazsAttendant(this);
    }
}
