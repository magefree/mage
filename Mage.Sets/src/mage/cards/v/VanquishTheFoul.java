
package mage.cards.v;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class VanquishTheFoul extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with power 4 or greater");
    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 3));
    }

    public VanquishTheFoul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{5}{W}");


        // Destroy target creature with power 4 or greater. Scry 1.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        Target target = new TargetCreaturePermanent(filter);
        this.getSpellAbility().addTarget(target);
        this.getSpellAbility().addEffect(new ScryEffect(1));
    }

    private VanquishTheFoul(final VanquishTheFoul card) {
        super(card);
    }

    @Override
    public VanquishTheFoul copy() {
        return new VanquishTheFoul(this);
    }
}
