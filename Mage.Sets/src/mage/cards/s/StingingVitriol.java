package mage.cards.s;

import java.util.UUID;

import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetOpponent;

/**
 *
 * @author muz
 */
public final class StingingVitriol extends CardImpl {

    public StingingVitriol(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}{R}");

        // Stinging Vitriol deals 2 damage to target opponent. That player reveals their hand. You choose a nonland card from it. They discard that card.
        this.getSpellAbility().addEffect(new DamageTargetEffect(2));
        this.getSpellAbility().addEffect(new DiscardCardYouChooseTargetEffect(StaticFilters.FILTER_CARD_NON_LAND)
            .setText("That player reveals their hand. You choose a nonland card from it. They discard that card"));
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private StingingVitriol(final StingingVitriol card) {
        super(card);
    }

    @Override
    public StingingVitriol copy() {
        return new StingingVitriol(this);
    }
}
