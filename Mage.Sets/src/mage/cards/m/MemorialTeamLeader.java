package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.WarpAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MemorialTeamLeader extends CardImpl {

    public MemorialTeamLeader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.KAVU);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // During your turn, other creatures you control get +1/+0.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostControlledEffect(1, 0, Duration.WhileOnBattlefield, true),
                MyTurnCondition.instance, "during your turn, other creatures you control get +1/+0"
        )));

        // Warp {1}{R}
        this.addAbility(new WarpAbility(this, "{1}{R}"));
    }

    private MemorialTeamLeader(final MemorialTeamLeader card) {
        super(card);
    }

    @Override
    public MemorialTeamLeader copy() {
        return new MemorialTeamLeader(this);
    }
}
