

package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
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
public final class KnotvineMystic extends CardImpl {

    public KnotvineMystic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}{G}{W}");


        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        
        // {1}, {T}: Add {R}{G}{W}.
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, new Mana(1, 0, 0, 1, 1, 0, 0, 0), new TapSourceCost());
        ability.addCost(new GenericManaCost(1));
        this.addAbility(ability);
    }

    private KnotvineMystic(final KnotvineMystic card) {
        super(card);
    }

    @Override
    public KnotvineMystic copy() {
        return new KnotvineMystic(this);
    }

}
