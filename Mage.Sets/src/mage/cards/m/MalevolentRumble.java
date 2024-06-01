package mage.cards.m;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.RevealLibraryPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.filter.StaticFilters;
import mage.game.permanent.token.EldraziSpawnToken;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class MalevolentRumble extends CardImpl {

    public MalevolentRumble(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // Reveal the top four cards of your library. You may put a permanent card from among them into your hand. Put the rest into your graveyard. Create a 0/1 colorless Eldrazi Spawn creature token with "Sacrifice this creature: Add {C}."
        this.getSpellAbility().addEffect(new RevealLibraryPickControllerEffect(
                4, 1, StaticFilters.FILTER_CARD_PERMANENT, PutCards.HAND, PutCards.GRAVEYARD
        ));
        this.getSpellAbility().addEffect(new CreateTokenEffect(new EldraziSpawnToken()));
    }

    private MalevolentRumble(final MalevolentRumble card) {
        super(card);
    }

    @Override
    public MalevolentRumble copy() {
        return new MalevolentRumble(this);
    }
}
