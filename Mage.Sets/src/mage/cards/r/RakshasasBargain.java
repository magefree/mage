package mage.cards.r;

import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RakshasasBargain extends CardImpl {

    public RakshasasBargain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2/B}{2/G}{2/U}");

        // Look at the top four cards of your library. Put two of them into your hand and the rest into your graveyard.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(
                4, 2, PutCards.HAND, PutCards.GRAVEYARD
        ));
    }

    private RakshasasBargain(final RakshasasBargain card) {
        super(card);
    }

    @Override
    public RakshasasBargain copy() {
        return new RakshasasBargain(this);
    }
}
