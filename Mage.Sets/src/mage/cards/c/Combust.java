

package mage.cards.c;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CantBeCounteredSourceEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class Combust extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("white or blue creature");

    static {
        filter.add(Predicates.or(
                new ColorPredicate(ObjectColor.WHITE),
                new ColorPredicate(ObjectColor.BLUE)));
    }

    public Combust(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{R}");

        // Combust deals 5 damage to target white or blue creature. The damage can't be prevented.
        this.getSpellAbility().addEffect(new DamageTargetEffect(5, false));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
        
        // Combust can't be countered.
        Ability ability = new SimpleStaticAbility(Zone.STACK, new CantBeCounteredSourceEffect());
        ability.setRuleAtTheTop(true);
        this.addAbility(ability);
    }

    private Combust(final Combust card) {
        super(card);
    }

    @Override
    public Combust copy() {
        return new Combust(this);
    }

}