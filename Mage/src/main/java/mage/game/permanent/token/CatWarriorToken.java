

package mage.game.permanent.token;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mage.MageInt;
import mage.constants.CardType;
import mage.abilities.keyword.ForestwalkAbility;
import mage.constants.SubType;

/**
 *
 * @author spjspj
 */
public final class CatWarriorToken extends TokenImpl {

    static final private List<String> tokenImageSets = new ArrayList<>();

    static {
        tokenImageSets.addAll(Arrays.asList("C17", "C18"));
    }

    public CatWarriorToken() {
        super("Cat Warrior Token", "2/2 green Cat Warrior creature token with forestwalk");
        availableImageSetCodes = tokenImageSets;
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.color.setGreen(true);
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.WARRIOR);
        this.cardType.add(CardType.CREATURE);
        this.addAbility(new ForestwalkAbility());
    }

    public CatWarriorToken(final CatWarriorToken token) {
        super(token);
    }

    public CatWarriorToken copy() {
        return new CatWarriorToken(this);
    }
}
