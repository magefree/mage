package mage.cards.d;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DecanterOfEndlessWater extends CardImpl {

    public DecanterOfEndlessWater(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // You have no maximum hand size.
        this.addAbility(new SimpleStaticAbility(new MaximumHandSizeControllerEffect(
                Integer.MAX_VALUE, Duration.WhileOnBattlefield,
                MaximumHandSizeControllerEffect.HandSizeModification.SET
        )));

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());
    }

    private DecanterOfEndlessWater(final DecanterOfEndlessWater card) {
        super(card);
    }

    @Override
    public DecanterOfEndlessWater copy() {
        return new DecanterOfEndlessWater(this);
    }
}
