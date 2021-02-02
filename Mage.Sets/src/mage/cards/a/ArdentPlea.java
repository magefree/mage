
package mage.cards.a;

import java.util.UUID;
import mage.abilities.keyword.CascadeAbility;
import mage.abilities.keyword.ExaltedAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Loki
 */
public final class ArdentPlea extends CardImpl {

    public ArdentPlea(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}{U}");

        // Exalted (Whenever a creature you control attacks alone, that creature gets +1/+1 until end of turn.)
        this.addAbility(new ExaltedAbility());

        // Cascade (When you cast this spell, exile cards from the top of your library until you exile a nonland card that costs less. You may cast it without paying its mana cost. Put the exiled cards on the bottom in a random order.)
        this.addAbility(new CascadeAbility());
    }

    private ArdentPlea(final ArdentPlea card) {
        super(card);
    }

    @Override
    public ArdentPlea copy() {
        return new ArdentPlea(this);
    }
}
