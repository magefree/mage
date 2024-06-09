package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.PreventAllDamageToAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author LoneFox
 */
public final class DivineLight extends CardImpl {

    public DivineLight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{W}");

        // Prevent all damage that would be dealt this turn to creatures you control.
        this.getSpellAbility().addEffect(new PreventAllDamageToAllEffect(Duration.EndOfTurn, StaticFilters.FILTER_CONTROLLED_CREATURES)
                .setText("Prevent all damage that would be dealt this turn to creatures you control.")
        );
    }

    private DivineLight(final DivineLight card) {
        super(card);
    }

    @Override
    public DivineLight copy() {
        return new DivineLight(this);
    }
}
