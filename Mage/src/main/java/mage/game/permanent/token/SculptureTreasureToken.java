package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.token.TreasureAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author muz
 */
public final class SculptureTreasureToken extends TokenImpl {

    public SculptureTreasureToken() {
        super("Sculpture Treasure Token", "1/1 colorless Sculpture Treasure artifact creature token with \"{T}, Sacrifice this token: Add mana of any color.\"");
        cardType.add(CardType.CREATURE);
        cardType.add(CardType.ARTIFACT);
        subtype.add(SubType.SCULPTURE);
        subtype.add(SubType.TREASURE);
        power = new MageInt(1);
        toughness = new MageInt(1);

        this.addAbility(new TreasureAbility(false));
    }

    private SculptureTreasureToken(final SculptureTreasureToken token) {
        super(token);
    }

    public SculptureTreasureToken copy() {
        return new SculptureTreasureToken(this);
    }

}
