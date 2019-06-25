package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DaggersailAeronaut extends CardImpl {

    public DaggersailAeronaut(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // As long as it's your turn, Daggersail Aeronaut has flying.
        this.addAbility(new SimpleStaticAbility(
                new ConditionalContinuousEffect(new GainAbilitySourceEffect(
                        FlyingAbility.getInstance(), Duration.WhileOnBattlefield
                ), MyTurnCondition.instance, "As long as it's your turn, {this} has flying.")
        ));
    }

    private DaggersailAeronaut(final DaggersailAeronaut card) {
        super(card);
    }

    @Override
    public DaggersailAeronaut copy() {
        return new DaggersailAeronaut(this);
    }
}
