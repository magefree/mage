package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.CounterAnyPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FormationBreaker extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();
    private static final FilterPermanent filter2 = new FilterControlledCreaturePermanent();

    static {
        filter2.add(CounterAnyPredicate.instance);
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter2);
    private static final Hint hint = new ConditionHint(condition, "You control a creature with a counter on it");

    public FormationBreaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Creatures with power less than this creature's power can't block it.
        this.addAbility(new SimpleStaticAbility(new CantBeBlockedByCreaturesSourceEffect(filter, Duration.WhileOnBattlefield)
                .setText("creatures with power less than this creature's power can't block it")));

        // As long as you control a creature with a counter on it, this creature gets +1/+2.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostSourceEffect(1, 2, Duration.WhileOnBattlefield),
                condition, "as long as you control a creature with a counter on it, this creature gets +1/+2"
        )).addHint(hint));
    }

    private FormationBreaker(final FormationBreaker card) {
        super(card);
    }

    @Override
    public FormationBreaker copy() {
        return new FormationBreaker(this);
    }
}
