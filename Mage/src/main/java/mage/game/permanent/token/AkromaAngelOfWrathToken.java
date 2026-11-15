package mage.game.permanent.token;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 * @author muz
 */
public final class AkromaAngelOfWrathToken extends TokenImpl {

    public AkromaAngelOfWrathToken() {
        super("Akroma, Angel of Wrath", "Akroma, Angel of Wrath token");
        manaCost = new ManaCostsImpl<>("{5}{W}{W}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.ANGEL);
        power = new MageInt(6);
        toughness = new MageInt(6);

        // Flying, first strike, vigilance, trample, haste, protection from black and from red
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(FirstStrikeAbility.getInstance());
        this.addAbility(VigilanceAbility.getInstance());
        this.addAbility(TrampleAbility.getInstance());
        this.addAbility(HasteAbility.getInstance());
        this.addAbility(ProtectionAbility.from(ObjectColor.BLACK, ObjectColor.RED));
    }

    private AkromaAngelOfWrathToken(final AkromaAngelOfWrathToken token) {
        super(token);
    }

    @Override
    public AkromaAngelOfWrathToken copy() {
        return new AkromaAngelOfWrathToken(this);
    }
}
