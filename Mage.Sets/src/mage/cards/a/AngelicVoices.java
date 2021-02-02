package mage.cards.a;

import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AngelicVoices extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("nonartifact, nonwhite creatures");

    static {
        filter.add(Predicates.not(
                Predicates.or(
                        CardType.ARTIFACT.getPredicate(),
                        new ColorPredicate(ObjectColor.WHITE)
                )
        ));
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    private static final Condition condition = new InvertCondition(new PermanentsOnTheBattlefieldCondition(filter));

    public AngelicVoices(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{W}");

        // Creatures you control get +1/+1 as long as you control no nonartifact, nonwhite creatures.
        this.addAbility(new SimpleStaticAbility(
                new ConditionalContinuousEffect(
                        new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield), condition,
                        "Creatures you control get +1/+1 as long as you control no nonartifact, nonwhite creatures."
                )
        ));
    }

    private AngelicVoices(final AngelicVoices card) {
        super(card);
    }

    @Override
    public AngelicVoices copy() {
        return new AngelicVoices(this);
    }
}
