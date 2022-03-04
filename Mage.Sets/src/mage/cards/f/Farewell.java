package mage.cards.f;

import mage.abilities.Mode;
import mage.abilities.effects.common.ExileAllEffect;
import mage.abilities.effects.common.ExileGraveyardAllPlayersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Farewell extends CardImpl {

    public Farewell(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{W}{W}");

        // Choose one or more —
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(4);

        // • Exile all artifacts.
        this.getSpellAbility().addEffect(new ExileAllEffect(StaticFilters.FILTER_PERMANENT_ARTIFACTS));

        // • Exile all creatures.
        this.getSpellAbility().addMode(new Mode(new ExileAllEffect(StaticFilters.FILTER_PERMANENT_CREATURES)));

        // • Exile all enchantments.
        this.getSpellAbility().addMode(new Mode(new ExileAllEffect(StaticFilters.FILTER_PERMANENT_ENCHANTMENTS)));

        // • Exile all graveyards.
        this.getSpellAbility().addMode(new Mode(new ExileGraveyardAllPlayersEffect()));
    }

    private Farewell(final Farewell card) {
        super(card);
    }

    @Override
    public Farewell copy() {
        return new Farewell(this);
    }
}
