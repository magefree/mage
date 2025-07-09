package mage.cards.l;

import mage.MageInt;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInHandCondition;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author ilcartographer
 */
public final class LavabornMuse extends CardImpl {

    private static final Condition condition = new CardsInHandCondition(ComparisonType.FEWER_THAN, 3, TargetController.ACTIVE);

    public LavabornMuse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // At the beginning of each opponent's upkeep, if that player has two or fewer cards in hand, Lavaborn Muse deals 3 damage to that player.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                TargetController.OPPONENT,
                new DamageTargetEffect(3, true, "that player"),
                false
        ).withInterveningIf(condition));
    }

    private LavabornMuse(final LavabornMuse card) {
        super(card);
    }

    @Override
    public LavabornMuse copy() {
        return new LavabornMuse(this);
    }
}
