package mage.cards.l;

import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LoftyDenial extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);

    public LoftyDenial(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Counter target spell unless its controller pays {1}. If you control a creature with flying, counter that spell unless its controller pays {4} instead.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new CounterUnlessPaysEffect(new GenericManaCost(4)),
                new CounterUnlessPaysEffect(new GenericManaCost(1)),
                condition, "counter target spell unless its controller pays {1}. " +
                "If you control a creature with flying, counter that spell unless its controller pays {4} instead"
        ));
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private LoftyDenial(final LoftyDenial card) {
        super(card);
    }

    @Override
    public LoftyDenial copy() {
        return new LoftyDenial(this);
    }
}
