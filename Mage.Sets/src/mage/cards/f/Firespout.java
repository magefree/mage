
package mage.cards.f;

import mage.abilities.condition.common.ManaWasSpentCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class Firespout extends CardImpl {

    private static final FilterCreaturePermanent filter1 = new FilterCreaturePermanent("creature without flying");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("creature with flying");

    static {
        filter1.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
        filter2.add(new AbilityPredicate(FlyingAbility.class));
    }

    public Firespout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R/G}");


        // Firespout deals 3 damage to each creature without flying if {R} was spent to cast Firespout and 3 damage to each creature with flying if {G} was spent to cast it.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DamageAllEffect(3, filter1),
                ManaWasSpentCondition.RED, "{this} deals 3 damage to each creature without flying if {R} was spent to cast this spell"));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DamageAllEffect(3, filter2),
                ManaWasSpentCondition.GREEN, "and 3 damage to each creature with flying if {G} was spent to cast this spell. <i>(Do both if {R}{G} was spent.)</i>"));
    }

    private Firespout(final Firespout card) {
        super(card);
    }

    @Override
    public Firespout copy() {
        return new Firespout(this);
    }
}
