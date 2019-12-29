package mage.cards.t;

import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class TormentingVoice extends CardImpl {

    public TormentingVoice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}");

        // As an additional cost to cast Tormenting Voice, discard a card.
        this.getSpellAbility().addCost(new DiscardCardCost());
        // Draw two cards.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));
    }

    private TormentingVoice(final TormentingVoice card) {
        super(card);
    }

    @Override
    public TormentingVoice copy() {
        return new TormentingVoice(this);
    }
}
