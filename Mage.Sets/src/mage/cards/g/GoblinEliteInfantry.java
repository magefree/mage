
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BlocksOrBecomesBlockedSourceTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author Loki
 */
public final class GoblinEliteInfantry extends CardImpl {

    public GoblinEliteInfantry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.addAbility(new BlocksOrBecomesBlockedSourceTriggeredAbility(new BoostSourceEffect(-1, -1, Duration.EndOfTurn), false));
    }

    private GoblinEliteInfantry(final GoblinEliteInfantry card) {
        super(card);
    }

    @Override
    public GoblinEliteInfantry copy() {
        return new GoblinEliteInfantry(this);
    }
}
