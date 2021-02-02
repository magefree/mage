package mage.cards.s;

import java.util.UUID;
import mage.Mana;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.mana.DynamicManaEffect;
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
        DynamicManaEffect effect = new DynamicManaEffect(Mana.BlackMana(1), new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_CREATURE));
        this.getSpellAbility().addEffect(effect);
    }

    private SongsOfTheDamned(final SongsOfTheDamned card) {
        super(card);
    }

    @Override
    public SongsOfTheDamned copy() {
        return new SongsOfTheDamned(this);
    }
}
