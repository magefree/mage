package mage.cards.a;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.ShuffleYourGraveyardIntoLibraryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class ArchangelsLight extends CardImpl {

    private static final DynamicValue xValue = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD, 2);

    public ArchangelsLight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{7}{W}");

        // You gain 2 life for each card in your graveyard, then shuffle your graveyard into your library.
        this.getSpellAbility().addEffect(new GainLifeEffect(xValue, "you gain 2 life for each card in your graveyard"));
        this.getSpellAbility().addEffect(new ShuffleYourGraveyardIntoLibraryEffect().concatBy(", then"));

    }

    private ArchangelsLight(final ArchangelsLight card) {
        super(card);
    }

    @Override
    public ArchangelsLight copy() {
        return new ArchangelsLight(this);
    }
}
