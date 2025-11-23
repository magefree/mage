package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.hint.common.LandsYouControlHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterControlledLandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KyoshiWarriorExemplars extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterControlledLandPermanent("you control eight or more lands"), ComparisonType.MORE_THAN, 7
    );

    public KyoshiWarriorExemplars(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Whenever this creature attacks, if you control eight or more lands, creatures you control get +2/+2 until end of turn.
        this.addAbility(new AttacksTriggeredAbility(
                new BoostControlledEffect(2, 2, Duration.EndOfTurn)
        ).withInterveningIf(condition).addHint(LandsYouControlHint.instance));
    }

    private KyoshiWarriorExemplars(final KyoshiWarriorExemplars card) {
        super(card);
    }

    @Override
    public KyoshiWarriorExemplars copy() {
        return new KyoshiWarriorExemplars(this);
    }
}
