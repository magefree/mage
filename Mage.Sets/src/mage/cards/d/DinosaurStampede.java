
package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author TheElk801
 */
public final class DinosaurStampede extends CardImpl {

    private static final FilterAttackingCreature filter = new FilterAttackingCreature("Attacking creatures");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("Dinosaurs you control");

    static {
        filter2.add(new SubtypePredicate(SubType.DINOSAUR));
        filter2.add(new ControllerPredicate(TargetController.YOU));
    }

    public DinosaurStampede(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Attacking creatures get +2/+0 until end of turn. Dinosaurs you control gain trample until end of turn.
        this.getSpellAbility().addEffect(new BoostAllEffect(2, 0, Duration.EndOfTurn, filter, false));
        Effect effect = new GainAbilityControlledEffect(TrampleAbility.getInstance(), Duration.EndOfTurn, filter2);
        effect.setText("Dinosaurs you control gain trample until end of turn.");
        this.getSpellAbility().addEffect(effect);
    }

    public DinosaurStampede(final DinosaurStampede card) {
        super(card);
    }

    @Override
    public DinosaurStampede copy() {
        return new DinosaurStampede(this);
    }
}
