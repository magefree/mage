package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class Pegasus22Token extends TokenImpl {

    public Pegasus22Token() {
        super("Pegasus Token", "2/2 white Pegasus creature token with flying");
        this.cardType.add(CardType.CREATURE);
        this.color.setWhite(true);
        this.subtype.add(SubType.PEGASUS);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.addAbility(FlyingAbility.getInstance());
    }

    private Pegasus22Token(final Pegasus22Token token) {
        super(token);
    }

    public Pegasus22Token copy() {
        return new Pegasus22Token(this);
    }
}
