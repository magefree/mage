package mage.cards.i;

import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.keyword.AmassEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterInstantOrSorceryCard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvadeTheCity extends CardImpl {

    public static final FilterInstantOrSorceryCard filter
            = new FilterInstantOrSorceryCard("instant and sorcery cards");

    public InvadeTheCity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}{R}");

        // Amass X, where X is the number of instant and sorcery cards in your graveyard.
        this.getSpellAbility().addEffect(new AmassEffect(new CardsInControllerGraveyardCount(filter)));
    }

    private InvadeTheCity(final InvadeTheCity card) {
        super(card);
    }

    @Override
    public InvadeTheCity copy() {
        return new InvadeTheCity(this);
    }
}
