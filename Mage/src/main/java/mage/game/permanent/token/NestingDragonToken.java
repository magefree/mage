package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author spjspj
 */
public final class NestingDragonToken extends TokenImpl {

    public NestingDragonToken() {
        super(
                "Dragon Egg Token",
                "0/2 red Dragon Egg creature token with defender and "
                + "\""
                + "When this creature dies, "
                + "create a 2/2 red Dragon creature token with flying and "
                + "'{R}: This creature gets +1/+0 until end of turn.'"
                + "\""
        );
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.DRAGON);
        subtype.add(SubType.EGG);
        power = new MageInt(0);
        toughness = new MageInt(2);
        addAbility(DefenderAbility.getInstance());
        this.addAbility(new DiesSourceTriggeredAbility(
                new CreateTokenEffect(new DragonEggDragonToken()), false
        ));
    }

    public NestingDragonToken(final NestingDragonToken token) {
        super(token);
    }

    public NestingDragonToken copy() {
        return new NestingDragonToken(this);
    }
}
