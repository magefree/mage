package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.NotMyTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
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
public final class MesaLynx extends CardImpl {

    public MesaLynx(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.CAT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // As long as it's not your turn, Mesa Lynx gets +0/+2.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(0, 2, Duration.WhileOnBattlefield),
                NotMyTurnCondition.instance, "as long as it's not your turn, {this} gets +0/+2"
        )));
    }

    private MesaLynx(final MesaLynx card) {
        super(card);
    }

    @Override
    public MesaLynx copy() {
        return new MesaLynx(this);
    }
}
