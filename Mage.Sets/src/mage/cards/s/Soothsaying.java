
package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LookLibraryControllerEffect;
import mage.abilities.effects.common.ShuffleLibrarySourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author emerald000
 */
public final class Soothsaying extends CardImpl {

    public Soothsaying(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{U}");


        // {3}{U}{U}: Shuffle your library.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new ShuffleLibrarySourceEffect(), new ManaCostsImpl<>("{3}{U}{U}")));
        
        // {X}: Look at the top X cards of your library, then put them back in any order.
        Effect effect = new LookLibraryControllerEffect(ManacostVariableValue.REGULAR);
        effect.setText("Look at the top X cards of your library, then put them back in any order");
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl<>("{X}")));
    }

    private Soothsaying(final Soothsaying card) {
        super(card);
    }

    @Override
    public Soothsaying copy() {
        return new Soothsaying(this);
    }
}
