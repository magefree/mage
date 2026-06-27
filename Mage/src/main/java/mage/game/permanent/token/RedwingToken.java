package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 * @author muz
 */
public final class RedwingToken extends TokenImpl {

    public RedwingToken() {
        super("Redwing", "Redwing, a legendary 1/1 blue Bird Scout creature token with "
            + "flying and \"Whenever Redwing attacks, surveil 1.\"");
        this.cardType.add(CardType.CREATURE);
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.SCOUT);
        this.color.setBlue(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new AttacksTriggeredAbility(new SurveilEffect(1), false));
    }

    private RedwingToken(final RedwingToken token) {
        super(token);
    }

    public RedwingToken copy() {
        return new RedwingToken(this);
    }

}
