

package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.LookLibraryControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetSpell;

/**
 *
 * @author LevelX
 */
public final class Discombobulate extends CardImpl {

    public Discombobulate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{U}{U}");

                // Counter target spell. Look at the top four cards of your library, then put them back in any order.
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addEffect(new CounterTargetEffect());
                this.getSpellAbility().addEffect(new LookLibraryControllerEffect(4));
    }

    private Discombobulate(final Discombobulate card) {
        super(card);
    }

    @Override
    public Discombobulate copy() {
        return new Discombobulate(this);
    }

}
