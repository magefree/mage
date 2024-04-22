
package mage.cards.b;

import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class BitterTriumph extends CardImpl {

    public BitterTriumph(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // As an additional cost to cast this spell, discard a card or pay 3 life.
        this.getSpellAbility().addCost(
                new OrCost(
                        "discard a card or pay 3 life",
                        new DiscardCardCost(),
                        new PayLifeCost(3)
                )
        );

        // Destroy target creature or planeswalker.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
    }

    private BitterTriumph(final BitterTriumph card) {
        super(card);
    }

    @Override
    public BitterTriumph copy() {
        return new BitterTriumph(this);
    }
}
