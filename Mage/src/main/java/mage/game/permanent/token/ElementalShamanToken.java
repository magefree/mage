
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
 * @author Styxo
 */
public final class ElementalShamanToken extends TokenImpl {

    static final private List<String> tokenImageSets = new ArrayList<>();

    static {
        tokenImageSets.addAll(Arrays.asList("C15", "JVC", "DD2", "LRW"));
    }

    public ElementalShamanToken(boolean withHaste) {
        this("DD2");
        if (withHaste) {
            addAbility(HasteAbility.getInstance());
            description = description + " with haste";
        }
    }

    public ElementalShamanToken() {
        this("LRW");
    }

    public ElementalShamanToken(String setCode) {
        super("Elemental Shaman Token", "3/1 red Elemental Shaman creature token");
        availableImageSetCodes = tokenImageSets;
        setOriginalExpansionSetCode(setCode);
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.ELEMENTAL);
        subtype.add(SubType.SHAMAN);
        power = new MageInt(3);
        toughness = new MageInt(1);
    }

    public ElementalShamanToken(final ElementalShamanToken token) {
        super(token);
    }

    public ElementalShamanToken copy() {
        return new ElementalShamanToken(this);
    }
}
