

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;

/**
 *
 * @author spjspj
 */
public final class CrushOfTentaclesToken extends TokenImpl {

    public CrushOfTentaclesToken() {
        super("Octopus Token", "8/8 blue Octopus creature");
        this.setExpansionSetCodeForImage("BFZ");
        this.cardType.add(CardType.CREATURE);
        this.color.setBlue(true);
        this.subtype.add(SubType.OCTOPUS);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);
    }
    public CrushOfTentaclesToken(final CrushOfTentaclesToken token) {
        super(token);
    }

    public CrushOfTentaclesToken copy() {
        return new CrushOfTentaclesToken(this);
    }
    
}
