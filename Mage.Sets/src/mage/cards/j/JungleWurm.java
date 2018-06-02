
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesBlockedTriggeredAbility;
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
 * @author LoneFox
 */
public final class JungleWurm extends CardImpl {

    public JungleWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}");
        this.subtype.add(SubType.WURM);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Whenever Jungle Wurm becomes blocked, it gets -1/-1 until end of turn for each creature blocking it beyond the first.
        BlockedCreatureCount blockedCreatureCount = new BlockedCreatureCount("each creature blocking it beyond the first", true);
        DynamicValue value = new MultipliedValue(blockedCreatureCount, -1);
        Effect effect = new BoostSourceEffect(value, value, Duration.EndOfTurn, true);
        effect.setText("it gets -1/-1 until end of turn for each creature blocking it beyond the first");
        this.addAbility(new BecomesBlockedTriggeredAbility(effect, false));
    }

    public JungleWurm(final JungleWurm card) {
        super(card);
    }

    @Override
    public JungleWurm copy() {
        return new JungleWurm(this);
    }
}
