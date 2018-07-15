package mage.cards.c;

import java.util.UUID;

import mage.abilities.effects.common.InfoEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author noahg
 */
public final class ContractFromBelow extends CardImpl {

    public ContractFromBelow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}");
        

        // Remove Contract from Below from your deck before playing if you're not playing for ante.
        // Discard your hand, add the top card of your library to the ante, then draw seven cards.
        this.getSpellAbility().addEffect(new InfoEffect("Remove Contract from Below from your deck before playing " +
                "if you're not playing for ante.\nDiscard your hand, add the top card of your library to the ante, then" +
                " draw seven cards."));
    }

    public ContractFromBelow(final ContractFromBelow card) {
        super(card);
    }

    @Override
    public ContractFromBelow copy() {
        return new ContractFromBelow(this);
    }
}
