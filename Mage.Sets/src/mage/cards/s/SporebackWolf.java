package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.hint.common.MyTurnHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SporebackWolf extends CardImpl {

    public SporebackWolf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.WOLF);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // As long as it's your turn, Sporeback Wolf gets +0/+2.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(0, 2, Duration.WhileOnBattlefield),
                MyTurnCondition.instance, "as long as it's your turn, {this} gets +0/+2."
        )).addHint(MyTurnHint.instance));
    }

    private SporebackWolf(final SporebackWolf card) {
        super(card);
    }

    @Override
    public SporebackWolf copy() {
        return new SporebackWolf(this);
    }
}
