
package mage.game.permanent.token;

import mage.MageInt;
import mage.Mana;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.mana.SimpleManaAbility;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

public final class EldraziSliverToken extends TokenImpl {

    public EldraziSliverToken() {
        super("Eldrazi Sliver Token", "1/1 colorless Eldrazi Sliver creature token with \"Sacrifice this creature: Add {C}.\"");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.ELDRAZI);
        subtype.add(SubType.SLIVER);
        power = new MageInt(1);
        toughness = new MageInt(1);
        addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.ColorlessMana(1), new SacrificeSourceCost()));
    }

    public EldraziSliverToken(final EldraziSliverToken token) {
        super(token);
    }

    @Override
    public EldraziSliverToken copy() {
        return new EldraziSliverToken(this); //To change body of generated methods, choose Tools | Templates.
    }
}
