package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

public final class XiraBlackInsectToken extends TokenImpl {

    public XiraBlackInsectToken() {
        super("Insect Token", "1/1 black Insect creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.INSECT);
        power = new MageInt(1);
        toughness = new MageInt(1);
        this.addAbility(FlyingAbility.getInstance());
        setOriginalExpansionSetCode("DMC");
    }

    public XiraBlackInsectToken(final XiraBlackInsectToken token) {
        super(token);
    }

    public XiraBlackInsectToken copy() {
        return new XiraBlackInsectToken(this);
    }
}
