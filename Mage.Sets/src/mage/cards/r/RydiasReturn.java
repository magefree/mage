package mage.cards.r;

import mage.abilities.Mode;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RydiasReturn extends CardImpl {

    public RydiasReturn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}{G}");

        // Choose one --
        // * Creatures you control get +3/+3 until end of turn.
        this.getSpellAbility().addEffect(new BoostControlledEffect(3, 3, Duration.EndOfTurn));

        // * Return up to two target permanent cards from your graveyard to your hand.
        this.getSpellAbility().addMode(new Mode(new ReturnFromGraveyardToHandTargetEffect())
                .addTarget(new TargetCardInYourGraveyard(0, 2, StaticFilters.FILTER_CARD_PERMANENTS)));
    }

    private RydiasReturn(final RydiasReturn card) {
        super(card);
    }

    @Override
    public RydiasReturn copy() {
        return new RydiasReturn(this);
    }
}
