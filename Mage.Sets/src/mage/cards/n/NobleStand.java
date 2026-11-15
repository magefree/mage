package mage.cards.n;

import java.util.UUID;
import mage.abilities.common.BlocksAllTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

/**
 *
 * @author Galatolol
 */
public final class NobleStand extends CardImpl {

    public NobleStand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{W}");

        // Whenever a creature you control blocks, you gain 2 life.
        this.addAbility(new BlocksAllTriggeredAbility(
                new GainLifeEffect(2), false, StaticFilters.FILTER_CONTROLLED_CREATURE
        ));
    }

    private NobleStand(final NobleStand card) {
        super(card);
    }

    @Override
    public NobleStand copy() {
        return new NobleStand(this);
    }
}
