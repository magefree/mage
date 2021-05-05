
package mage.cards.e;

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
 * @author ilcartographer
 */
public final class ElvishBerserker extends CardImpl {

    public ElvishBerserker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Elvish Berserker becomes blocked, it gets +1/+1 until end of turn for each creature blocking it.
        BlockedCreatureCount value = BlockedCreatureCount.ALL;
        Effect effect = new BoostSourceEffect(value, value, Duration.EndOfTurn, true);
        effect.setText("it gets +1/+1 until end of turn for each creature blocking it");
        this.addAbility(new BecomesBlockedSourceTriggeredAbility(effect, false));
    }

    private ElvishBerserker(final ElvishBerserker card) {
        super(card);
    }

    @Override
    public ElvishBerserker copy() {
        return new ElvishBerserker(this);
    }
}
