package mage.cards.f;

import mage.abilities.effects.common.RevealAndSeparatePilesEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author North
 */
public final class FactOrFiction extends CardImpl {

    public FactOrFiction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}");

        // Reveal the top five cards of your library. An opponent separates those cards into two piles. Put one pile into your hand and the other into your graveyard.
        this.getSpellAbility().addEffect(new RevealAndSeparatePilesEffect(
                5, TargetController.OPPONENT, TargetController.YOU, Zone.GRAVEYARD
        ));
    }

    private FactOrFiction(final FactOrFiction card) {
        super(card);
    }

    @Override
    public FactOrFiction copy() {
        return new FactOrFiction(this);
    }
}
