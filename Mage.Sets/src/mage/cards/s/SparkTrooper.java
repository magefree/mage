
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

/**
 *
 * @author LevelX2
 */
public final class SparkTrooper extends CardImpl {

    public SparkTrooper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}{R}{W}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(6);
        this.toughness = new MageInt(1);


        // Trample, lifelink, haste
        this.addAbility(TrampleAbility.getInstance());
        this.addAbility(LifelinkAbility.getInstance());
        this.addAbility(HasteAbility.getInstance());

        // At the beginning of the end step, sacrifice Spark Trooper.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new SacrificeSourceEffect(), TargetController.NEXT, false));

    }

    private SparkTrooper(final SparkTrooper card) {
        super(card);
    }

    @Override
    public SparkTrooper copy() {
        return new SparkTrooper(this);
    }
}
