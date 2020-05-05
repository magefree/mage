package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.util.RandomUtil;

public final class HumanSoldierToken extends TokenImpl {

    public HumanSoldierToken() {
        super("Human Soldier", "1/1 white Human Soldier creature token");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.HUMAN);
        subtype.add(SubType.SOLDIER);
        color.setWhite(true);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("IKO")) {
            setTokenType(RandomUtil.nextInt(3) + 1); // 1...3
        }
    }

    public HumanSoldierToken(final HumanSoldierToken token) {
        super(token);
    }

    public HumanSoldierToken copy() {
        return new HumanSoldierToken(this);
    }
}
