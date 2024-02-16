package mage.cards.c;

import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceTargetsPermanentCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author AhmadYProjects
 */
public final class CarnivorousCanopy extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifact, enchantment, or creature with flying");
    private static final FilterPermanent filter2 = new FilterPermanent();


    static {
        filter.add(Predicates.or(
                        CardType.ARTIFACT.getPredicate(),
                        CardType.ENCHANTMENT.getPredicate(),
                        Predicates.and(
                                CardType.CREATURE.getPredicate(),
                                new AbilityPredicate(FlyingAbility.class)
                        )
                )
        );
        filter2.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    private static final Condition condition = new SourceTargetsPermanentCondition(filter2);

    public CarnivorousCanopy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Destroy target artifact, enchantment, or creature with flying. If that permanent's mana value was 3 or less, proliferate.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new ProliferateEffect(), condition, "If that permanent's mana value was 3 or less, proliferate. " +
                "<i>(Choose any number of permanents and/or players, then give each another counter of each kind already there.)</i>"
        ));
    }

    private CarnivorousCanopy(final CarnivorousCanopy card) {
        super(card);
    }

    @Override
    public CarnivorousCanopy copy() {
        return new CarnivorousCanopy(this);
    }
}
