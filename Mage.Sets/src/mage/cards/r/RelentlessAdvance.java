package mage.cards.r;

import mage.abilities.effects.keyword.AmassEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RelentlessAdvance extends CardImpl {

    public RelentlessAdvance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}");

        // Amass 3. (Put three +1/+1 counters on an Army you control. If you don't control one, create a 0/0 black Zombie Army creature token first.
        this.getSpellAbility().addEffect(new AmassEffect(3, SubType.ZOMBIE));
    }

    private RelentlessAdvance(final RelentlessAdvance card) {
        super(card);
    }

    @Override
    public RelentlessAdvance copy() {
        return new RelentlessAdvance(this);
    }
}
