
package mage.cards.w;

import java.util.UUID;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class WasteAway extends CardImpl {

    public WasteAway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{4}{B}");

        // As an additional cost to cast Waste Away, discard a card.
        this.getSpellAbility().addCost(new DiscardCardCost());
        // Target creature gets -5/-5 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(-5, -5, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private WasteAway(final WasteAway card) {
        super(card);
    }

    @Override
    public WasteAway copy() {
        return new WasteAway(this);
    }
}
