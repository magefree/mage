package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.DoombotToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DoctorDoom extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("you control an artifact creature or a Plan");

    static {
        filter.add(Predicates.or(
                Predicates.and(
                        CardType.ARTIFACT.getPredicate(),
                        CardType.CREATURE.getPredicate()
                ),
                SubType.PLAN.getPredicate()
        ));
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);
    private static final Hint hint = new ConditionHint(condition);

    public DoctorDoom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCIENTIST);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Doctor Doom enters, create two 3/3 colorless Robot Villain artifact creature tokens named Doombot.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new DoombotToken(), 2)));

        // As long as you control an artifact creature or a Plan, Doctor Doom has indestructible.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(IndestructibleAbility.getInstance()), condition,
                "as long as you control an artifact creature or a Plan, {this} has indestructible"
        )).addHint(hint));

        // At the beginning of your end step, you draw a card and lose 1 life.
        Ability ability = new BeginningOfEndStepTriggeredAbility(new DrawCardSourceControllerEffect(1, true));
        ability.addEffect(new LoseLifeSourceControllerEffect(1, false).concatBy("and"));
        this.addAbility(ability);
    }

    private DoctorDoom(final DoctorDoom card) {
        super(card);
    }

    @Override
    public DoctorDoom copy() {
        return new DoctorDoom(this);
    }
}
