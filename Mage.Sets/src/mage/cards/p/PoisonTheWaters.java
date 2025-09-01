package mage.cards.p;

import mage.abilities.Mode;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PoisonTheWaters extends CardImpl {

    public PoisonTheWaters(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Choose one --
        // * All creatures get -1/-1 until end of turn.
        this.getSpellAbility().addEffect(new BoostAllEffect(-1, -1, Duration.EndOfTurn));

        // * Target player reveals their hand. You choose an artifact or creature card from it. That player discards that card.
        this.getSpellAbility().addMode(new Mode(new DiscardCardYouChooseTargetEffect(StaticFilters.FILTER_CARD_ARTIFACT_OR_CREATURE)).addTarget(new TargetPlayer()));
    }

    private PoisonTheWaters(final PoisonTheWaters card) {
        super(card);
    }

    @Override
    public PoisonTheWaters copy() {
        return new PoisonTheWaters(this);
    }
}
