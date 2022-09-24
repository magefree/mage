package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class DinosaurHasteToken extends TokenImpl {

    public DinosaurHasteToken() {
        super("Dinosaur Token", "1/1 red Dinosaur creature token with haste");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.DINOSAUR);
        power = new MageInt(1);
        toughness = new MageInt(1);
        addAbility(HasteAbility.getInstance());
        setOriginalExpansionSetCode("IKO");
    }

    private DinosaurHasteToken(final DinosaurHasteToken token) {
        super(token);
    }

    public DinosaurHasteToken copy() {
        return new DinosaurHasteToken(this);
    }
}
