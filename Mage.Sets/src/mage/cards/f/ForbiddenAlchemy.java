package mage.cards.f;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.LookLibraryControllerEffect.PutCards;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author North
 */
public final class ForbiddenAlchemy extends CardImpl {

    public ForbiddenAlchemy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Look at the top four cards of your library. Put one of them into your hand and the rest into your graveyard.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(4, 1, PutCards.HAND, PutCards.GRAVEYARD));

        // Flashback {6}{B}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{6}{B}")));
    }

    private ForbiddenAlchemy(final ForbiddenAlchemy card) {
        super(card);
    }

    @Override
    public ForbiddenAlchemy copy() {
        return new ForbiddenAlchemy(this);
    }
}
