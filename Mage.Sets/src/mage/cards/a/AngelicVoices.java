
package mage.cards.a;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author TheElk801
 */
public final class AngelicVoices extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("nonartifact, nonwhite creatures");

    static {
        filter.add(Predicates.not(
                Predicates.or(
                        new CardTypePredicate(CardType.ARTIFACT),
                        new ColorPredicate(ObjectColor.WHITE)
                )
        ));
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public AngelicVoices(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{W}");

        // Creatures you control get +1/+1 as long as you control no nonartifact, nonwhite creatures.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(
                        new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield),
                        new InvertCondition(new PermanentsOnTheBattlefieldCondition(filter)),
                        "Creatures you control get +1/+1 as long as you control no nonartifact, nonwhite creatures."
                )
        ));
    }

    public AngelicVoices(final AngelicVoices card) {
        super(card);
    }

    @Override
    public AngelicVoices copy() {
        return new AngelicVoices(this);
    }
}
