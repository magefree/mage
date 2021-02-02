
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CycleAllTriggeredAbility;
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
public final class StoicChampion extends CardImpl {

    public StoicChampion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever a player cycles a card, Stoic Champion gets +2/+2 until end of turn.
        this.addAbility(new CycleAllTriggeredAbility(new BoostSourceEffect(2,2,Duration.EndOfTurn), false));
    }

    private StoicChampion(final StoicChampion card) {
        super(card);
    }

    @Override
    public StoicChampion copy() {
        return new StoicChampion(this);
    }
}
