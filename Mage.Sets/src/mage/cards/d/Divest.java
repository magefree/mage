package mage.cards.d;

import java.util.UUID;

import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.constants.TargetController;
import mage.target.TargetPlayer;

/**
 *
 * @author MasterSamurai
 *
 */
public final class Divest extends CardImpl {

    public Divest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}");

        // Target player reveals their hand. You choose an artifact or creature card from it. That player discards that card.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new DiscardCardYouChooseTargetEffect(StaticFilters.FILTER_CARD_ARTIFACT_OR_CREATURE, TargetController.ANY));
    }

    private Divest(final Divest card) {
        super(card);
    }

    @Override
    public Divest copy() {
        return new Divest(this);
    }
}
