package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TilonallisKnight extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterControlledPermanent(SubType.DINOSAUR, "you control a Dinosaur")
    );

    public TilonallisKnight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Tilonalli's Knight attacks, if you control a Dinosaur, Tilonalli's Knight gets +1/+1 until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new BoostSourceEffect(1, 1, Duration.EndOfTurn)).withInterveningIf(condition));
    }

    private TilonallisKnight(final TilonallisKnight card) {
        super(card);
    }

    @Override
    public TilonallisKnight copy() {
        return new TilonallisKnight(this);
    }
}
