
package mage.cards.p;

import java.util.UUID;
import mage.Mana;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class PyreticRitual extends CardImpl {

    public PyreticRitual(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{R}");

        // Add {R}{R}{R}.
        this.getSpellAbility().addEffect(new BasicManaEffect(Mana.RedMana(3)));
    }

    private PyreticRitual(final PyreticRitual card) {
        super(card);
    }

    @Override
    public PyreticRitual copy() {
        return new PyreticRitual(this);
    }
}
