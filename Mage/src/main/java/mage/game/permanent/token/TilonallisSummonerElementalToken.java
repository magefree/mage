package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author LevelX2
 */
public final class TilonallisSummonerElementalToken extends TokenImpl {

    public TilonallisSummonerElementalToken() {
        super("Elemental", "1/1 red Elemental creature tokens");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.ELEMENTAL);
        color.setRed(true);
        power = new MageInt(1);
        toughness = new MageInt(1);

        setTokenType(2);
    }

    public TilonallisSummonerElementalToken(final TilonallisSummonerElementalToken token) {
        super(token);
    }

    @Override
    public TilonallisSummonerElementalToken copy() {
        return new TilonallisSummonerElementalToken(this);
    }
}
