package mage.cards.l;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.LeylineAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author emerald000
 */
public final class LeylineOfTheMeek extends CardImpl {

    public LeylineOfTheMeek(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{W}");

        // If Leyline of the Meek is in your opening hand, you may begin the game with it on the battlefield.
        this.addAbility(LeylineAbility.getInstance());

        // Creature tokens get +1/+1.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new BoostAllEffect(
                        1, 1, Duration.WhileOnBattlefield,
                        StaticFilters.FILTER_CREATURE_TOKENS, false
                )
        ));
    }

    private LeylineOfTheMeek(final LeylineOfTheMeek card) {
        super(card);
    }

    @Override
    public LeylineOfTheMeek copy() {
        return new LeylineOfTheMeek(this);
    }
}
