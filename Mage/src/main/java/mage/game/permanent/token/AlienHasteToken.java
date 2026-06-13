
package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.abilities.keyword.HasteAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author muz
 */
public final class AlienHasteToken extends TokenImpl {

    public AlienHasteToken() {
        super("Alien Token", "1/1 red Alien creature token with haste and \"This creature attacks each combat if able.\"");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.ALIEN);
        power = new MageInt(1);
        toughness = new MageInt(1);
        this.addAbility(HasteAbility.getInstance());
        this.addAbility(new AttacksEachCombatStaticAbility());
    }

    private AlienHasteToken(final AlienHasteToken token) {
        super(token);
    }

    public AlienHasteToken copy() {
        return new AlienHasteToken(this);
    }
}
