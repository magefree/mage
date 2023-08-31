package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPlayer;

/**
 *
 * @author jonubuu
 */
public final class Thoughtseize extends CardImpl {

    public Thoughtseize(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}");

        // Target player reveals their hand. You choose a nonland card from it. That player discards that card. You lose 2 life.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new DiscardCardYouChooseTargetEffect(StaticFilters.FILTER_CARD_NON_LAND));
        this.getSpellAbility().addEffect(new LoseLifeSourceControllerEffect(2));
    }

    private Thoughtseize(final Thoughtseize card) {
        super(card);
    }

    @Override
    public Thoughtseize copy() {
        return new Thoughtseize(this);
    }
}
