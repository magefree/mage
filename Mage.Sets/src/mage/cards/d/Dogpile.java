
package mage.cards.d;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.common.FilterAttackingCreature;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author LevelX2
 */
public final class Dogpile extends CardImpl {

    private static final FilterAttackingCreature filter = new FilterAttackingCreature("attacking creatures you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public Dogpile(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{R}");

        // Dogpile deals damage to any target equal to the number of attacking creatures you control.
        this.getSpellAbility().addEffect(new DamageTargetEffect(new PermanentsOnBattlefieldCount(filter)).
                setText("{this} deals damage to any target equal to the number of attacking creatures you control"));
        this.getSpellAbility().addTarget(new TargetAnyTarget());

    }

    private Dogpile(final Dogpile card) {
        super(card);
    }

    @Override
    public Dogpile copy() {
        return new Dogpile(this);
    }
}
