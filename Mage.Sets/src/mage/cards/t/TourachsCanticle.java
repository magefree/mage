package mage.cards.t;

import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TourachsCanticle extends CardImpl {

    public TourachsCanticle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}");

        // Target opponent reveals their hand. You choose a card from it. That player discards that card, then discards a card at random.
        this.getSpellAbility().addEffect(new DiscardCardYouChooseTargetEffect());
        this.getSpellAbility().addEffect(new DiscardTargetEffect(1, true)
                .setText(", then discards a card at random"));
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private TourachsCanticle(final TourachsCanticle card) {
        super(card);
    }

    @Override
    public TourachsCanticle copy() {
        return new TourachsCanticle(this);
    }
}
