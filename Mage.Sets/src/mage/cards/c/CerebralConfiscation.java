package mage.cards.c;

import mage.abilities.Mode;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CerebralConfiscation extends CardImpl {

    public CerebralConfiscation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Choose one --
        // * Target opponent discards two cards.
        this.getSpellAbility().addEffect(new DiscardTargetEffect(2));
        this.getSpellAbility().addTarget(new TargetOpponent());

        // * Target opponent reveals their hand. You choose a nonland card from it. That player discards that card.
        this.getSpellAbility().addMode(new Mode(
                new DiscardCardYouChooseTargetEffect(StaticFilters.FILTER_CARD_NON_LAND)
        ).addTarget(new TargetOpponent()));
    }

    private CerebralConfiscation(final CerebralConfiscation card) {
        super(card);
    }

    @Override
    public CerebralConfiscation copy() {
        return new CerebralConfiscation(this);
    }
}
