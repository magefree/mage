package mage.cards.a;

import mage.abilities.effects.common.ExileAllEffect;
import mage.abilities.effects.common.discard.DiscardHandControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author markedagain
 */
public final class Apocalypse extends CardImpl {

    public Apocalypse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}{R}{R}");

        // Exile all permanents. You discard your hand.
        this.getSpellAbility().addEffect(new ExileAllEffect(StaticFilters.FILTER_PERMANENTS));
        this.getSpellAbility().addEffect(new DiscardHandControllerEffect().setText("You discard your hand"));
    }

    private Apocalypse(final Apocalypse card) {
        super(card);
    }

    @Override
    public Apocalypse copy() {
        return new Apocalypse(this);
    }
}
