package mage.game.permanent.token;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author muz
 */
public final class AxeToken extends TokenImpl {

    public AxeToken() {
        super("Axe", "colorless Equipment artifact token named Axe with \"Equipped creature gets +1/+0\" and equip {2}");
        cardType.add(CardType.ARTIFACT);
        subtype.add(SubType.EQUIPMENT);

        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(1, 0)));

        this.addAbility(new EquipAbility(2));
    }

    private AxeToken(final AxeToken token) {
        super(token);
    }

    public AxeToken copy() {
        return new AxeToken(this);
    }
}
