package mage.game.permanent.token;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class SwordToken extends TokenImpl {

    public SwordToken() {
        super("Sword", "colorless Equipment artifact token named Sword with \"Equipped creature gets +1/+1\" and equip {2}");
        cardType.add(CardType.ARTIFACT);
        subtype.add(SubType.EQUIPMENT);

        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(1, 1)));

        this.addAbility(new EquipAbility(2));
    }

    private SwordToken(final SwordToken token) {
        super(token);
    }

    public SwordToken copy() {
        return new SwordToken(this);
    }
}
