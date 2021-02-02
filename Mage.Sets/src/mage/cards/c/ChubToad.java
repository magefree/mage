
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BlocksOrBecomesBlockedSourceTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author anonymous
 */
public final class ChubToad extends CardImpl {

    public ChubToad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.FROG);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Chub Toad blocks or becomes blocked, it gets +2/+2 until end of turn.
        Effect effect = new BoostSourceEffect(+2, +2, Duration.EndOfTurn);
        effect.setText("it gets +2/+2 until end of turn");
        Ability ability = new BlocksOrBecomesBlockedSourceTriggeredAbility(effect, false);
        this.addAbility(ability);
    }

    private ChubToad(final ChubToad card) {
        super(card);
    }

    @Override
    public ChubToad copy() {
        return new ChubToad(this);
    }
}
