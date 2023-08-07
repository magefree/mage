
package mage.cards.i;

import java.util.UUID;
import mage.abilities.condition.LockedInCondition;
import mage.abilities.condition.common.ManaWasSpentCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.LoseAbilityAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 *
 * @author jeffwadsworth
 *
 */
public final class InvertTheSkies extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
        filter.add(new AbilityPredicate(FlyingAbility.class));
        filter.add(CardType.CREATURE.getPredicate());
    }

    public InvertTheSkies(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{G/U}");

        // Creatures your opponents control lose flying until end of turn if {G} was spent to cast Invert the Skies, and creatures you control gain flying until end of turn if {U} was spent to cast it.
        this.getSpellAbility().addEffect(new ConditionalContinuousEffect(
                new LoseAbilityAllEffect(FlyingAbility.getInstance(), Duration.EndOfTurn, filter),
                new LockedInCondition(ManaWasSpentCondition.GREEN),
                "Creatures your opponents control lose flying until end of turn if {G} was spent to cast this spell,"));

        this.getSpellAbility().addEffect(new ConditionalContinuousEffect(
                new GainAbilityControlledEffect(FlyingAbility.getInstance(), Duration.EndOfTurn),
                new LockedInCondition(ManaWasSpentCondition.BLUE),
                "and creatures you control gain flying until end of turn if {U} was spent to cast this spell"));

        this.getSpellAbility().addEffect(new InfoEffect("<i>(Do both if {G}{U} was spent.)</i>"));

    }

    private InvertTheSkies(final InvertTheSkies card) {
        super(card);
    }

    @Override
    public InvertTheSkies copy() {
        return new InvertTheSkies(this);
    }
}
