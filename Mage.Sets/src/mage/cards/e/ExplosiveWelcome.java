package mage.cards.e;

import mage.Mana;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ManaType;
import mage.filter.common.FilterCreaturePlayerOrPlaneswalker;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.target.Target;
import mage.target.common.TargetAnyTarget;
import mage.target.targetpointer.SecondTargetPointer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ExplosiveWelcome extends CardImpl {

    private static final FilterCreaturePlayerOrPlaneswalker filter = new FilterCreaturePlayerOrPlaneswalker();

    static {
        filter.getPermanentFilter().add(new AnotherTargetPredicate(2));
        filter.getPlayerFilter().add(new AnotherTargetPredicate(2));
    }

    public ExplosiveWelcome(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{7}{R}");

        // Explosive Welcome deals 5 damage to any target and 3 damage to any other target. Add {R}{R}{R}.
        this.getSpellAbility().addEffect(new DamageTargetEffect(StaticValue.get(5), true, "", true));
        this.getSpellAbility().addEffect(
                new DamageTargetEffect(StaticValue.get(5), true, "", true)
                        .setTargetPointer(new SecondTargetPointer())
                        .setText("and 3 damage to any other target.")
        );
        this.getSpellAbility().addEffect(new BasicManaEffect(new Mana(ManaType.RED, 3)));
        Target target = new TargetAnyTarget();
        target.setTargetTag(1);
        this.getSpellAbility().addTarget(target);
        target = new TargetAnyTarget(filter);
        target.setTargetTag(2);
        this.getSpellAbility().addTarget(target);
    }

    private ExplosiveWelcome(final ExplosiveWelcome card) {
        super(card);
    }

    @Override
    public ExplosiveWelcome copy() {
        return new ExplosiveWelcome(this);
    }
}
