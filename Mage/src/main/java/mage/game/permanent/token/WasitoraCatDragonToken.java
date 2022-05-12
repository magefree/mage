
package mage.game.permanent.token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;

/**
 *
 * @author spjspj
 */
public final class WasitoraCatDragonToken extends TokenImpl {

    static final private List<String> tokenImageSets = new ArrayList<>();

    static {
        tokenImageSets.addAll(Arrays.asList("C17"));
    }
    
    public WasitoraCatDragonToken() {
        super("Cat Dragon Token", "3/3 black, red, and green Cat Dragon creature token with flying");
        
        availableImageSetCodes = tokenImageSets;
        setOriginalExpansionSetCode("C17");

        cardType.add(CardType.CREATURE);
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.DRAGON);
        color.setBlack(true);
        color.setRed(true);
        color.setGreen(true);
        power = new MageInt(3);
        toughness = new MageInt(3);
        this.addAbility(FlyingAbility.getInstance());
    }

    public WasitoraCatDragonToken(final WasitoraCatDragonToken token) {
        super(token);
    }

    public WasitoraCatDragonToken copy() {
        return new WasitoraCatDragonToken(this);
    }
}
