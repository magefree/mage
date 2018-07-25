package mage.cards.s;

import java.util.UUID;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class StasisWard extends CardImpl {

    public StasisWard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}{U}");
        

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Stasis Ward enters the battlefield, counter target spell. If that spell is countered this way, exile that card until Stasis Ward leaves the battlefield.
    }

    public StasisWard(final StasisWard card) {
        super(card);
    }

    @Override
    public StasisWard copy() {
        return new StasisWard(this);
    }
}
