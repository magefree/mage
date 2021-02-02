package mage.cards.b;

import mage.abilities.effects.common.BalanceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class Balance extends CardImpl {

    public Balance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{W}");

        // Each player chooses a number of lands they control equal to the number of lands controlled by the player who controls the fewest, then sacrifices the rest. Players discard cards and sacrifice creatures the same way.
        this.getSpellAbility().addEffect(new BalanceEffect());
    }

    private Balance(final Balance card) {
        super(card);
    }

    @Override
    public Balance copy() {
        return new Balance(this);
    }
}
