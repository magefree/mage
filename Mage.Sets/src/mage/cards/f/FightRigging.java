package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.HideawayPlayEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.HideawayAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FightRigging extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent();

    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 6));
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);

    public FightRigging(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        // Hideaway 5
        this.addAbility(new HideawayAbility(5));

        // At the beginning of combat on your turn, put a +1/+1 counter on target creature you control. Then if you control a creature with power 7 or greater, you may play the exiled card without paying its mana cost.
        Ability ability = new BeginningOfCombatTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()),
                TargetController.YOU, false
        );
        ability.addEffect(new ConditionalOneShotEffect(
                new HideawayPlayEffect(), condition, "Then if you control a creature " +
                "with power 7 or greater, you may play the exiled card without paying its mana cost"
        ));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private FightRigging(final FightRigging card) {
        super(card);
    }

    @Override
    public FightRigging copy() {
        return new FightRigging(this);
    }
}
