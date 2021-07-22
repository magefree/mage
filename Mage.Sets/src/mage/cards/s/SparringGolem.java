
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesBlockedSourceTriggeredAbility;
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
 * @author fireshoes
 */
public final class SparringGolem extends CardImpl {

    public SparringGolem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{3}");
        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Sparring Golem becomes blocked, it gets +1/+1 until end of turn for each creature blocking it.
        BlockedCreatureCount value = BlockedCreatureCount.ALL;
        Effect effect = new BoostSourceEffect(value, value, Duration.EndOfTurn, true);
        effect.setText("it gets +1/+1 until end of turn for each creature blocking it");
        this.addAbility(new BecomesBlockedSourceTriggeredAbility(effect, false));
    }

    private SparringGolem(final SparringGolem card) {
        super(card);
    }

    @Override
    public SparringGolem copy() {
        return new SparringGolem(this);
    }
}
