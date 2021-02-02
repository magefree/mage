
package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.DamageEverythingEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Quercitron
 */
public final class DrySpell extends CardImpl {

    public DrySpell(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{B}");


        // Dry Spell deals 1 damage to each creature and each player.
        this.getSpellAbility().addEffect(new DamageEverythingEffect(1));
    }

    private DrySpell(final DrySpell card) {
        super(card);
    }

    @Override
    public DrySpell copy() {
        return new DrySpell(this);
    }
}
