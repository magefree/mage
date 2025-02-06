package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.MaxSpeedAbility;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.StartYourEnginesAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EndriderSpikespitter extends CardImpl {

    public EndriderSpikespitter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MERCENARY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Start your engines!
        this.addAbility(new StartYourEnginesAbility());

        // Max speed -- At the beginning of your upkeep, exile the top card of your library. You may play that card this turn.
        this.addAbility(new MaxSpeedAbility(new BeginningOfUpkeepTriggeredAbility(new ExileTopXMayPlayUntilEffect(1, Duration.EndOfTurn))));
    }

    private EndriderSpikespitter(final EndriderSpikespitter card) {
        super(card);
    }

    @Override
    public EndriderSpikespitter copy() {
        return new EndriderSpikespitter(this);
    }
}
