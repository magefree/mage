package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.MenaceAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author muz
 */
public final class VillainToken extends TokenImpl {

    public VillainToken() {
        super("Villain Token", "2/1 black Villain creature token with menace");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.VILLAIN);
        power = new MageInt(2);
        toughness = new MageInt(1);
        this.addAbility(new MenaceAbility());
    }

    private VillainToken(final VillainToken token) {
        super(token);
    }

    @Override
    public VillainToken copy() {
        return new VillainToken(this);
    }
}
