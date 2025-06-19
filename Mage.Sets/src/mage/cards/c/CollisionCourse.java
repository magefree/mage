package mage.cards.c;

import mage.abilities.Mode;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CollisionCourse extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent("permanents you control that are creatures and/or Vehicles");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                SubType.VEHICLE.getPredicate()
        ));
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter, null);
    private static final Hint hint = new ValueHint("Creatures and Vehicles you control", xValue);

    public CollisionCourse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{W}");

        // Choose one --
        // * Collision Course deals X damage to target creature, where X is the number of permanents you control that are creatures and/or Vehicles.
        this.getSpellAbility().addEffect(new DamageTargetEffect(xValue)
                .setText("{this} deals X damage to target creature, where X is " +
                        "the number of permanents you control that are creatures and/or Vehicles"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addHint(hint);

        // * Destroy target artifact.
        this.getSpellAbility().addMode(new Mode(new DestroyTargetEffect()).addTarget(new TargetArtifactPermanent()));
    }

    private CollisionCourse(final CollisionCourse card) {
        super(card);
    }

    @Override
    public CollisionCourse copy() {
        return new CollisionCourse(this);
    }
}
