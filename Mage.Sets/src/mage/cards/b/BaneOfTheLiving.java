
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.MorphManacostVariableValue;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class BaneOfTheLiving extends CardImpl {

    public BaneOfTheLiving(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{B}");
        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Morph {X}{B}{B}
        this.addAbility(new MorphAbility(this, new ManaCostsImpl("{X}{B}{B}")));
        // When Bane of the Living is turned face up, all creatures get -X/-X until end of turn.
        DynamicValue morphX = new SignInversionDynamicValue(new MorphManacostVariableValue());
        this.addAbility(new TurnedFaceUpSourceTriggeredAbility(new BoostAllEffect(morphX, morphX, Duration.EndOfTurn, new FilterCreaturePermanent("all creatures"), false, "", true)));
    }

    public BaneOfTheLiving(final BaneOfTheLiving card) {
        super(card);
    }

    @Override
    public BaneOfTheLiving copy() {
        return new BaneOfTheLiving(this);
    }
}
