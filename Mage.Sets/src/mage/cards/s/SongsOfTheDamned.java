package mage.cards.s;

import java.util.UUID;
import mage.Mana;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.mana.DynamicManaEffect;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

/**
 *
 * @author fireshoes
 */
public final class SongsOfTheDamned extends CardImpl {
    public SongsOfTheDamned(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}");

        // Add {B} for each creature card in your graveyard.
        DynamicValue creatureCardsInGraveyard = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_CREATURES);
        DynamicManaEffect effect = new DynamicManaEffect(Mana.BlackMana(1), creatureCardsInGraveyard);
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addHint(new ValueHint("Creature cards in your graveyard", creatureCardsInGraveyard));
    }

    private SongsOfTheDamned(final SongsOfTheDamned card) {
        super(card);
    }

    @Override
    public SongsOfTheDamned copy() {
        return new SongsOfTheDamned(this);
    }
}
