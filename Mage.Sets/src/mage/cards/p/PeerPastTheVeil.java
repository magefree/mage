package mage.cards.p;

import mage.abilities.dynamicvalue.common.CardTypesInGraveyardCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardHandControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PeerPastTheVeil extends CardImpl {

    public PeerPastTheVeil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}{G}");

        // Discard your hand. Then draw X cards, where X is the number of card types among cards in your graveyard.
        this.getSpellAbility().addEffect(new DiscardHandControllerEffect());
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(CardTypesInGraveyardCount.YOU).concatBy("Then"));
        this.getSpellAbility().addHint(CardTypesInGraveyardCount.YOU.getHint());
    }

    private PeerPastTheVeil(final PeerPastTheVeil card) {
        super(card);
    }

    @Override
    public PeerPastTheVeil copy() {
        return new PeerPastTheVeil(this);
    }
}
