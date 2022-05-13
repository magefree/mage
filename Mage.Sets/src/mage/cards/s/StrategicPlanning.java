package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.LookLibraryControllerEffect.PutCards;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author LevelX2
 */
public final class StrategicPlanning extends CardImpl {

    public StrategicPlanning(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}");

        // Look at the top three cards of your library. Put one of them into your hand and the rest into your graveyard.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(3, 1, PutCards.HAND, PutCards.GRAVEYARD));
    }

    private StrategicPlanning(final StrategicPlanning card) {
        super(card);
    }

    @Override
    public StrategicPlanning copy() {
        return new StrategicPlanning(this);
    }
}
