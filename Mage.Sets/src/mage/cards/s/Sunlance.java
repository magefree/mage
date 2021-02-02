
package mage.cards.s;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class Sunlance extends CardImpl {

    static final protected FilterCreaturePermanent filter = new FilterCreaturePermanent("nonwhite creature");
    static {
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.WHITE)));
    }


    public Sunlance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{W}");


        // Sunlance deals 3 damage to target nonwhite creature.
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
    }

    private Sunlance(final Sunlance card) {
        super(card);
    }

    @Override
    public Sunlance copy() {
        return new Sunlance(this);
    }
}
