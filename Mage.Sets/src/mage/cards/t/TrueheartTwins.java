
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.ExertCreatureControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.ExertAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author stravant
 */
public final class TrueheartTwins extends CardImpl {

    public TrueheartTwins(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");
        
        this.subtype.add(SubType.JACKAL);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // You may exert Trueheart Twins as it attacks.
        this.addAbility(new ExertAbility(null, false));

        // Whenever you exert a creature, creatures you control get +1/+0 until end of turn.
        this.addAbility(new ExertCreatureControllerTriggeredAbility(new BoostControlledEffect(1, 0, Duration.EndOfTurn)));
    }

    private TrueheartTwins(final TrueheartTwins card) {
        super(card);
    }

    @Override
    public TrueheartTwins copy() {
        return new TrueheartTwins(this);
    }
}
