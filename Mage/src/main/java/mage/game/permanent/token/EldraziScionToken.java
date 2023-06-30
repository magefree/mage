package mage.game.permanent.token;

import mage.MageInt;
import mage.Mana;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.mana.SimpleManaAbility;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 * @author fireshoes
 */
public final class EldraziScionToken extends TokenImpl {

    public EldraziScionToken() {
        super("Eldrazi Scion Token", "1/1 colorless Eldrazi Scion creature token with \"Sacrifice this creature: Add {C}.\"");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.ELDRAZI);
        subtype.add(SubType.SCION);
        power = new MageInt(1);
        toughness = new MageInt(1);
        addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.ColorlessMana(1), new SacrificeSourceCost()));
    }

    public EldraziScionToken(final EldraziScionToken token) {
        super(token);
    }

    @Override
    public EldraziScionToken copy() {
        return new EldraziScionToken(this); //To change body of generated methods, choose Tools | Templates.
    }
}
