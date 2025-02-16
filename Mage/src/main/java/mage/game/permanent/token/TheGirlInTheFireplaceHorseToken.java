package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HorsemanshipAbility;
import mage.constants.Duration;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;

/**
 * @author padfoot 
 */
public final class TheGirlInTheFireplaceHorseToken extends TokenImpl {

    public TheGirlInTheFireplaceHorseToken() {
        super("Horse Token", "2/2 white Horse creature token with \"Doctors you control have horsemanship.\"");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.HORSE);
        power = new MageInt(2);
        toughness = new MageInt(2);
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(HorsemanshipAbility.getInstance(),
                Duration.WhileOnBattlefield, new FilterCreaturePermanent(SubType.DOCTOR, "Doctors"), false)));
    }

    private TheGirlInTheFireplaceHorseToken(final TheGirlInTheFireplaceHorseToken token) {
        super(token);
    }

    @Override
    public TheGirlInTheFireplaceHorseToken copy() {
        return new TheGirlInTheFireplaceHorseToken(this);
    }
}
