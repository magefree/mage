package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceAttackingCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author TheElk801
 */
public final class VividFlyingFish extends CardImpl {

    public VividFlyingFish(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.FISH);
        this.subtype.add(SubType.LIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Vivid Flying Fish has flying as long as it's attacking.        
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(
                        new GainAbilitySourceEffect(
                                FlyingAbility.getInstance(),
                                Duration.WhileOnBattlefield
                        ),
                        SourceAttackingCondition.instance,
                        "{this} has flying as long as it's attacking."
                )
        ));
    }

    private VividFlyingFish(final VividFlyingFish card) {
        super(card);
    }

    @Override
    public VividFlyingFish copy() {
        return new VividFlyingFish(this);
    }
}
