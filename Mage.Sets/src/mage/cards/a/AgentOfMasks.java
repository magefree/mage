package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.LoseLifeOpponentsYouGainLifeLostEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author Loki
 */
public final class AgentOfMasks extends CardImpl {

    public AgentOfMasks(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);

        // At the beginning of your upkeep, each opponent loses 1 life. You gain life equal to the life lost this way.
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new LoseLifeOpponentsYouGainLifeLostEffect(1), TargetController.YOU, false));
    }

    private AgentOfMasks(final AgentOfMasks card) {
        super(card);
    }

    @Override
    public AgentOfMasks copy() {
        return new AgentOfMasks(this);
    }
}
