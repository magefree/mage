
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.AwakenAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class SheerDrop extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("tapped creature");
    
    static {
        filter.add(TappedPredicate.TAPPED);
    }

    public SheerDrop(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{W}");

        // Destroy target tapped creature.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
        
        // Awaken 3-{5}{W} <i>(If you cast this spell for {5}{W}, also put three +1/+1 counters on target land you control and it becomes a 0/0 Elemental creature with haste. It's still a land.)<i>
        this.addAbility(new AwakenAbility(this, 3, "{5}{W}"));
    }

    private SheerDrop(final SheerDrop card) {
        super(card);
    }

    @Override
    public SheerDrop copy() {
        return new SheerDrop(this);
    }
}
