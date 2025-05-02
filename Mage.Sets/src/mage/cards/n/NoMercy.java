package mage.cards.n;

import mage.abilities.common.DealsDamageToYouAllTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 *
 * @author Plopman
 */
public final class NoMercy extends CardImpl {

    public NoMercy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}{B}");

        // Whenever a creature deals damage to you, destroy it.
        this.addAbility(new DealsDamageToYouAllTriggeredAbility(StaticFilters.FILTER_PERMANENT_CREATURE,
                new DestroyTargetEffect().setText("destroy it"), false));
    }

    private NoMercy(final NoMercy card) {
        super(card);
    }

    @Override
    public NoMercy copy() {
        return new NoMercy(this);
    }

}
