
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
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
 * @author LevelX2
 */
public final class SatyrHedonist extends CardImpl {

    public SatyrHedonist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.SATYR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {R}, Sacrifice Satyr Hedonist: Add {R}{R}{R}.
        SimpleManaAbility ability = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.RedMana(3), new ManaCostsImpl<>("{R}"));
        ability .addCost(new SacrificeSourceCost());
        this.addAbility(ability);

    }

    private SatyrHedonist(final SatyrHedonist card) {
        super(card);
    }

    @Override
    public SatyrHedonist copy() {
        return new SatyrHedonist(this);
    }
}
