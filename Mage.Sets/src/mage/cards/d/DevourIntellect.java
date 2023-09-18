package mage.cards.d;

import java.util.UUID;

import mage.abilities.condition.common.TreasureSpentToCastCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetOpponent;
import mage.watchers.common.ManaPaidSourceWatcher;

/**
 *
 * @author weirddan455
 */
public final class DevourIntellect extends CardImpl {

    public DevourIntellect(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}");

        // Target opponent discards a card. If mana from a Treasure was spent to cast this spell, instead that player reveals their hand, you choose a nonland card from it, then that player discards a card.
        this.getSpellAbility().addTarget(new TargetOpponent());
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DiscardCardYouChooseTargetEffect(StaticFilters.FILTER_CARD_NON_LAND),
                new DiscardTargetEffect(1),
                TreasureSpentToCastCondition.instance,
                "Target opponent discards a card. If mana from a Treasure was spent to cast this spell, instead that player reveals their hand, you choose a nonland card from it, then that player discards that card"
        ));
    }

    private DevourIntellect(final DevourIntellect card) {
        super(card);
    }

    @Override
    public DevourIntellect copy() {
        return new DevourIntellect(this);
    }
}
