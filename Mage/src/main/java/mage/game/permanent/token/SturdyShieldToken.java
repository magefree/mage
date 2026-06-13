package mage.game.permanent.token;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author muz
 */
public final class SturdyShieldToken extends TokenImpl {

    public SturdyShieldToken() {
        super("Sturdy Shield", "colorless Equipment artifact token named Sturdy Shield with \"Equipped creature gets +1/+2\" and equip {2}");
        cardType.add(CardType.ARTIFACT);
        subtype.add(SubType.EQUIPMENT);
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(1, 2)));
        this.addAbility(new EquipAbility(2));
    }

    private SturdyShieldToken(final SturdyShieldToken token) {
        super(token);
    }

    public SturdyShieldToken copy() {
        return new SturdyShieldToken(this);
    }
}
