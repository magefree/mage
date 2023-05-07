package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.MonstrosityAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author NinthWorld
 */
public final class PorgToken extends TokenImpl {

    public PorgToken() {
        super("Porg", "0/1 green Bird creature token named Porg with \"{G}: Monstrosity 1.\"");

        cardType.add(CardType.CREATURE);
        subtype.add(SubType.BIRD);

        color.setGreen(true);
        power = new MageInt(0);
        toughness = new MageInt(1);

        this.addAbility(new MonstrosityAbility("{G}", 1));
    }

    public PorgToken(final PorgToken token) {
        super(token);
    }

    public PorgToken copy() {
        return new PorgToken(this);
    }
}
