
package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.effects.common.DamageWithPowerFromOneToAnotherTargetEffect;

/**
 *
 * @author fireshoes, xenohedron
 */
public final class Deadshot extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another target creature");

    static {
        filter.add(new AnotherTargetPredicate(2));
    }

    public Deadshot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{R}");

        // Tap target creature.
        this.getSpellAbility().addEffect(new TapTargetEffect());
        TargetCreaturePermanent target = new TargetCreaturePermanent();
        target.setTargetTag(1);
        this.getSpellAbility().addTarget(target);

        // It deals damage equal to its power to another target creature.
        this.getSpellAbility().addEffect(new DamageWithPowerFromOneToAnotherTargetEffect("It"));
        target = new TargetCreaturePermanent(filter);
        target.setTargetTag(2);
        this.getSpellAbility().addTarget(target);
    }

    private Deadshot(final Deadshot card) {
        super(card);
    }

    @Override
    public Deadshot copy() {
        return new Deadshot(this);
    }
}

