package mage.cards.g;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.condition.common.NotMyTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.hint.common.MyTurnHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author Loki
 */
public final class GloryOfWarfare extends CardImpl {

    public GloryOfWarfare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}{W}");

        // As long as it’s your turn, creatures you control get +2/+0.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new BoostControlledEffect(2, 0, Duration.WhileOnBattlefield),
                MyTurnCondition.instance,
                "As long as it's your turn, creatures you control get +2/+0"))
                .addHint(MyTurnHint.instance));

        // As long as it’s not your turn, creatures you control get +0/+2.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new BoostControlledEffect(0, 2, Duration.WhileOnBattlefield),
                NotMyTurnCondition.instance,
                "As long as it's not your turn, creatures you control get +0/+2")));
    }

    private GloryOfWarfare(final GloryOfWarfare card) {
        super(card);
    }

    @Override
    public GloryOfWarfare copy() {
        return new GloryOfWarfare(this);
    }
}
