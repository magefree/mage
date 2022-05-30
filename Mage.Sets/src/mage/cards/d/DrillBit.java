package mage.cards.d;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.abilities.keyword.SpectacleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DrillBit extends CardImpl {

    public DrillBit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Target player reveals their hand. You choose a nonland card from it. That player discards that card.
        this.getSpellAbility().addEffect(new DiscardCardYouChooseTargetEffect(
                StaticFilters.FILTER_CARD_NON_LAND, TargetController.ANY
        ));
        this.getSpellAbility().addTarget(new TargetPlayer());

        // Spectacle {B}
        this.addAbility(new SpectacleAbility(this, new ManaCostsImpl<>("{B}")));
    }

    private DrillBit(final DrillBit card) {
        super(card);
    }

    @Override
    public DrillBit copy() {
        return new DrillBit(this);
    }
}
