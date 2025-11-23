package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.common.WaterbendCost;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RuthlessWaterbender extends CardImpl {

    public RuthlessWaterbender(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Waterbend {2}: This creature gets +1/+1 until end of turn. Activate only during your turn.
        this.addAbility(new ActivateIfConditionActivatedAbility(
                new BoostSourceEffect(1, 1, Duration.EndOfTurn),
                new WaterbendCost(2), MyTurnCondition.instance
        ));
    }

    private RuthlessWaterbender(final RuthlessWaterbender card) {
        super(card);
    }

    @Override
    public RuthlessWaterbender copy() {
        return new RuthlessWaterbender(this);
    }
}
