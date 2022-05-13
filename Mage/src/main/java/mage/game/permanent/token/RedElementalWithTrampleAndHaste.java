package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.util.RandomUtil;

/**
 * @author spjspj
 */
public final class RedElementalWithTrampleAndHaste extends TokenImpl {

    public RedElementalWithTrampleAndHaste() {
        super("Elemental Token", "7/1 red Elemental creature token with trample and haste");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.ELEMENTAL);
        power = new MageInt(7);
        toughness = new MageInt(1);
        addAbility(TrampleAbility.getInstance());
        addAbility(HasteAbility.getInstance());
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);

        if (getOriginalExpansionSetCode() != null && getOriginalExpansionSetCode().equals("ZEN")) {
            setTokenType(RandomUtil.nextInt(2) + 1);
        }
    }

    public RedElementalWithTrampleAndHaste(final RedElementalWithTrampleAndHaste token) {
        super(token);
    }

    public RedElementalWithTrampleAndHaste copy() {
        return new RedElementalWithTrampleAndHaste(this);
    }
}
