

package mage.game.permanent.token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author magenoxx
 */
public final class ElementalToken extends TokenImpl {

    static final private List<String> tokenImageSets = new ArrayList<>();

    static {
        tokenImageSets.addAll(Arrays.asList("OGW", "CON", "DIS"));
    }

    public ElementalToken() {
        this ("OGW", 0);
    }

    public ElementalToken(String setCode, int tokenType) {
        super("Elemental", "3/1 red Elemental creature token");
        availableImageSetCodes = tokenImageSets;
        setOriginalExpansionSetCode(setCode);
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.ELEMENTAL);
        power = new MageInt(3);
        toughness = new MageInt(1);

        this.setOriginalExpansionSetCode("CON");
    }
    
    public ElementalToken(String setCode, int tokenType, boolean hasHaste) {
        super("Elemental", "3/1 red Elemental creature token");
        setTokenType(tokenType);
        availableImageSetCodes = tokenImageSets;
        setOriginalExpansionSetCode(setCode);
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.ELEMENTAL);
        power = new MageInt(3);
        toughness = new MageInt(1);
        
        if (hasHaste) this.addAbility(HasteAbility.getInstance());
    }

    public ElementalToken(final ElementalToken token) {
        super(token);
    }

    public ElementalToken copy() {
        return new ElementalToken(this);
    }
}
