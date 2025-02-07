package mage.cards.a;

import java.util.UUID;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author sobiech
 */
public final class AetherfluxConduit extends CardImpl {

    public AetherfluxConduit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{6}");
        

        // Whenever you cast a spell, you get an amount of {E} equal to the amount of mana spent to cast that spell.
        // {T}, Pay fifty {E}: Draw seven cards. You may cast any number of spells from your hand without paying their mana costs.
    }

    private AetherfluxConduit(final AetherfluxConduit card) {
        super(card);
    }

    @Override
    public AetherfluxConduit copy() {
        return new AetherfluxConduit(this);
    }
}
