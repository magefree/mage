package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesTappedSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class ShuttleAce extends CardImpl {

    public ShuttleAce(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.subtype.add(SubType.KELPIEN);
        this.subtype.add(SubType.PILOT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // During your turn, this creature has flying.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
            new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.WhileOnBattlefield),
            MyTurnCondition.instance, "during your turn, {this} has flying"
        )));

        // Whenever this creature becomes tapped, you gain 2 life.
        this.addAbility(new BecomesTappedSourceTriggeredAbility(new GainLifeEffect(2)));
    }

    private ShuttleAce(final ShuttleAce card) {
        super(card);
    }

    @Override
    public ShuttleAce copy() {
        return new ShuttleAce(this);
    }
}
