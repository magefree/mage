package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.PreventDamageToSourceEffect;
import mage.abilities.keyword.VanishingAbility;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 * @author padfoot
 */
public final class TheGirlInTheFireplaceHumanNobleToken extends TokenImpl {

    public TheGirlInTheFireplaceHumanNobleToken() {
        super("Human Noble Token", "1/1 white Human Noble creature token with vanishing 3 and \"Prevent all damage that would be dealt to this token.\"");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.HUMAN,SubType.NOBLE);
        power = new MageInt(1);
        toughness = new MageInt(1);
	this.addAbility(new VanishingAbility(3));
	this.addAbility(new SimpleStaticAbility(
	        new PreventDamageToSourceEffect(
			Duration.WhileOnBattlefield, 
			Integer.MAX_VALUE
		).setText("Prevent all damage that would be dealt to this token.")
	));
    }

    private TheGirlInTheFireplaceHumanNobleToken(final TheGirlInTheFireplaceHumanNobleToken token) {
        super(token);
    }

    @Override
    public TheGirlInTheFireplaceHumanNobleToken copy() {
        return new TheGirlInTheFireplaceHumanNobleToken(this);
    }
}
