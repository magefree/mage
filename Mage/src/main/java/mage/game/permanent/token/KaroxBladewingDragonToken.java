package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 * @author JRHerlehy
 * Created on 4/5/18.
 */
public final class KaroxBladewingDragonToken extends TokenImpl {

    public KaroxBladewingDragonToken() {
        super("Karox Bladewing", "Karox Bladewing, a legendary 4/4 red Dragon creature token with flying");
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.supertype.add(SuperType.LEGENDARY);
        this.cardType.add(CardType.CREATURE);
        this.subtype.add(SubType.DRAGON);
        this.color.setRed(true);

        this.addAbility(FlyingAbility.getInstance());
    }

    public KaroxBladewingDragonToken(final KaroxBladewingDragonToken token) {
        super(token);
    }

    @Override
    public KaroxBladewingDragonToken copy() {
        return new KaroxBladewingDragonToken(this);
    }
}
