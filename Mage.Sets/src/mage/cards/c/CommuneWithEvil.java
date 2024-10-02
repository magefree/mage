package mage.cards.c;

import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CommuneWithEvil extends CardImpl {

    public CommuneWithEvil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Look at the top four cards of your library. Put one of them into your hand and the rest into your graveyard. You gain 3 life.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(
                4, 1, PutCards.HAND, PutCards.GRAVEYARD
        ));
        this.getSpellAbility().addEffect(new GainLifeEffect(3));
    }

    private CommuneWithEvil(final CommuneWithEvil card) {
        super(card);
    }

    @Override
    public CommuneWithEvil copy() {
        return new CommuneWithEvil(this);
    }
}
