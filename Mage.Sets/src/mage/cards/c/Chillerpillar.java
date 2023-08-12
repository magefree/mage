package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MonstrousCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MonstrosityAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Chillerpillar extends CardImpl {

    public Chillerpillar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.supertype.add(SuperType.SNOW);
        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {4}{S}{S}: Monstrosity 2.
        this.addAbility(new MonstrosityAbility("{4}{S}{S}", 2));

        // As long as Chillerpillar is monstrous, it has flying.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.WhileOnBattlefield),
                MonstrousCondition.instance, "As long as {this} is monstrous, it has flying."
        )));
    }

    private Chillerpillar(final Chillerpillar card) {
        super(card);
    }

    @Override
    public Chillerpillar copy() {
        return new Chillerpillar(this);
    }
}
