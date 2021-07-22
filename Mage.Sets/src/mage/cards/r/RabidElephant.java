
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesBlockedSourceTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.BlockedCreatureCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author LevelX2
 */
public final class RabidElephant extends CardImpl {

    public RabidElephant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add(SubType.ELEPHANT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Whenever Rabid Elephant becomes blocked, it gets +2/+2 until end of turn for each creature blocking it.
        DynamicValue value = new MultipliedValue(BlockedCreatureCount.ALL, 2);
        Effect effect = new BoostSourceEffect(value, value, Duration.EndOfTurn, true);
        effect.setText("it gets +2/+2 until end of turn for each creature blocking it");
        this.addAbility(new BecomesBlockedSourceTriggeredAbility(effect, false));
    }

    private RabidElephant(final RabidElephant card) {
        super(card);
    }

    @Override
    public RabidElephant copy() {
        return new RabidElephant(this);
    }
}
