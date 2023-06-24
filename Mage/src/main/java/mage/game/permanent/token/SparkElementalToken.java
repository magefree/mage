package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

/**
 * @author spjspj
 */
public final class SparkElementalToken extends TokenImpl {

    public SparkElementalToken() {
        super("Spark Elemental", "3/1 red Elemental creature token named Spark Elemental. It has trample, haste, and \"At the beginning of the end step, sacrifice Spark Elemental.\"");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.ELEMENTAL);
        power = new MageInt(3);
        toughness = new MageInt(1);

        this.addAbility(TrampleAbility.getInstance());
        this.addAbility(HasteAbility.getInstance());
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new SacrificeSourceEffect(), TargetController.ANY, false));
    }

    public SparkElementalToken(final SparkElementalToken token) {
        super(token);
    }

    public SparkElementalToken copy() {
        return new SparkElementalToken(this);
    }
}
