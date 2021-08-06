package mage.cards.m;

import mage.abilities.effects.common.RevealLibraryPutIntoHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author North
 */
public final class Mulch extends CardImpl {

    public Mulch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // Reveal the top four cards of your library. Put all land cards revealed this way into your hand and the rest into your graveyard.
        this.getSpellAbility().addEffect(new RevealLibraryPutIntoHandEffect(
                4, StaticFilters.FILTER_CARD_LANDS, Zone.GRAVEYARD
        ));
    }

    private Mulch(final Mulch card) {
        super(card);
    }

    @Override
    public Mulch copy() {
        return new Mulch(this);
    }
}
