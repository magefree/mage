

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.util.RandomUtil;

/**
 *
 * @author spjspj
 */
public final class MetallurgicSummoningsConstructToken extends TokenImpl {

    public MetallurgicSummoningsConstructToken() {
        this (1);
    }
    public MetallurgicSummoningsConstructToken(int xValue) {
        super("Construct", "X/X colorless Construct artifact creature token");
        setOriginalExpansionSetCode("KLD");
        setTokenType(RandomUtil.nextInt(2) + 1);
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.CONSTRUCT);
        power = new MageInt(xValue);
        toughness = new MageInt(xValue);
    }
    public MetallurgicSummoningsConstructToken(final MetallurgicSummoningsConstructToken token) {
        super(token);
    }

    public MetallurgicSummoningsConstructToken copy() {
        return new MetallurgicSummoningsConstructToken(this);
    }
}
