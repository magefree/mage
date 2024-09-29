package mage.cards.h;

import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
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
public final class HazelsNocturne extends CardImpl {

    public HazelsNocturne(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{B}");

        // Return up to two target creature cards from your graveyard to your hand. Each opponent loses 2 life and you gain 2 life.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(0, 2, StaticFilters.FILTER_CARD_CREATURES_YOUR_GRAVEYARD));
        this.getSpellAbility().addEffect(new LoseLifeOpponentsEffect(2));
        this.getSpellAbility().addEffect(new GainLifeEffect(2).concatBy("and"));
    }

    private HazelsNocturne(final HazelsNocturne card) {
        super(card);
    }

    @Override
    public HazelsNocturne copy() {
        return new HazelsNocturne(this);
    }
}
