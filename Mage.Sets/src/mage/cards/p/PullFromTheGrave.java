package mage.cards.p;

import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PullFromTheGrave extends CardImpl {

    public PullFromTheGrave(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Return up to two target creature cards from your graveyard to your hand. You gain 2 life.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(
                0, 2, StaticFilters.FILTER_CARD_CREATURES_YOUR_GRAVEYARD
        ));
        this.getSpellAbility().addEffect(new GainLifeEffect(2));
    }

    private PullFromTheGrave(final PullFromTheGrave card) {
        super(card);
    }

    @Override
    public PullFromTheGrave copy() {
        return new PullFromTheGrave(this);
    }
}
