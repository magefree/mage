package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.ReachAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class IzoniSpiderToken extends TokenImpl {

    public IzoniSpiderToken() {
        super("Spider Token", "2/1 black and green Spider creature token with menace and reach");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        color.setGreen(true);
        subtype.add(SubType.SPIDER);
        power = new MageInt(2);
        toughness = new MageInt(1);

        // Menace
        this.addAbility(new MenaceAbility());

        // Reach
        this.addAbility(ReachAbility.getInstance());
    }

    private IzoniSpiderToken(final IzoniSpiderToken token) {
        super(token);
    }

    public IzoniSpiderToken copy() {
        return new IzoniSpiderToken(this);
    }
}
