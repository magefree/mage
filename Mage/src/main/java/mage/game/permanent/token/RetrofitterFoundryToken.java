package mage.game.permanent.token;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

/**
 *
 * @author TheElk801
 */
public final class RetrofitterFoundryToken extends TokenImpl {

    public RetrofitterFoundryToken() {
        this("C18");
    }

    public RetrofitterFoundryToken(String setCode) {
        super("Construct", "4/4 colorless Construct artifact creature token");
        this.setOriginalExpansionSetCode(setCode);
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.CONSTRUCT);
        power = new MageInt(4);
        toughness = new MageInt(4);
    }

    public RetrofitterFoundryToken(final RetrofitterFoundryToken token) {
        super(token);
    }

    public RetrofitterFoundryToken copy() {
        return new RetrofitterFoundryToken(this);
    }
}
