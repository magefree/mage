
package mage.cards.p;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CantAttackYouUnlessPayAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author Loki
 */
public final class Propaganda extends CardImpl {

    public Propaganda(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{U}");

        // Creatures can't attack you unless their controller pays {2} for each creature they control that's attacking you.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantAttackYouUnlessPayAllEffect(new ManaCostsImpl<>("{2}"))));
    }

    private Propaganda(final Propaganda card) {
        super(card);
    }

    @Override
    public Propaganda copy() {
        return new Propaganda(this);
    }
}
