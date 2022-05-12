package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.constants.CardType;
import mage.constants.SubType;

public final class WallOfResurgenceToken extends TokenImpl {

    public WallOfResurgenceToken() {
        super(" Token", "0/0 Elemental creature with haste");
        this.cardType.add(CardType.CREATURE);

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        this.addAbility(HasteAbility.getInstance());
    }

    public WallOfResurgenceToken(final WallOfResurgenceToken token) {
        super(token);
    }

    public WallOfResurgenceToken copy() {
        return new WallOfResurgenceToken(this);
    }
}
