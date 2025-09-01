package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.AttacksOrBlocksTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class BurningSunCavalry extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterControlledPermanent(SubType.DINOSAUR, "you control a Dinosaur")
    );
    private static final Hint hint = new ConditionHint(condition, "You control a Dinosaur");

    public BurningSunCavalry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Burning Sun Cavalry attacks or blocks while you control a Dinosaur, Burning Sun Cavalry gets +1/+1 until end of turn.
        this.addAbility(new AttacksOrBlocksTriggeredAbility(
                new BoostSourceEffect(1, 1, Duration.EndOfTurn), false
        ).withTriggerCondition(condition).addHint(hint));
    }

    private BurningSunCavalry(final BurningSunCavalry card) {
        super(card);
    }

    @Override
    public BurningSunCavalry copy() {
        return new BurningSunCavalry(this);
    }
}
