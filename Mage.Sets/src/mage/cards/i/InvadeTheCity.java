package mage.cards.i;

import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.keyword.AmassEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvadeTheCity extends CardImpl {

    private static final Hint hint = new ValueHint(
            "Instants and sorceries in your graveyard",
            new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY)
    );

    public InvadeTheCity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}{R}");

        // Amass X, where X is the number of instant and sorcery cards in your graveyard.
        this.getSpellAbility().addEffect(
                new AmassEffect(
                        new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY),
                        SubType.ZOMBIE
                ).setText("amass Zombies X, where X is the number of instant and sorcery cards in your graveyard. "
                        + "<i>(Put X +1/+1 counters on an Army you control. It's also a Zombie. If you don't control an Army, "
                        + "create a 0/0 black Zombie Army creature token first.)</i>")
        );
    }

    private InvadeTheCity(final InvadeTheCity card) {
        super(card);
    }

    @Override
    public InvadeTheCity copy() {
        return new InvadeTheCity(this);
    }
}