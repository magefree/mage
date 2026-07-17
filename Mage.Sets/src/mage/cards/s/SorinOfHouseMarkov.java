package mage.cards.s;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.condition.common.YouGainedLifeCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.ControllerGainedLifeCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ExileAndReturnSourceEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTypeTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.ExtortAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.triggers.BeginningOfPostcombatMainTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.FoodToken;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.PlayerGainedLifeWatcher;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class SorinOfHouseMarkov extends TransformingDoubleFacedCard {

    private static final Condition condition = new YouGainedLifeCondition(ComparisonType.MORE_THAN, 2);
    private static final Hint hint = new ConditionHint(condition);

    private static final FilterPermanent filterWhite = new FilterPermanent("white permanent other than that creature or {this}");
    private static final Condition conditionWhite = new PermanentsOnTheBattlefieldCondition(filterWhite, true);
    private static final Hint hintWhite = new ConditionHint(conditionWhite, "you control another white permanent");

    static {
        filterWhite.add(new ColorPredicate(ObjectColor.WHITE));
        filterWhite.add(AnotherPredicate.instance);
        filterWhite.add(SorinRavenousNeonatePredicate.instance);
    }

    public SorinOfHouseMarkov(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.NOBLE}, "{1}{B}",
                "Sorin, Ravenous Neonate",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.PLANESWALKER}, new SubType[]{SubType.SORIN}, "WB"
        );

        // Sorin of House Markov
        this.getLeftHalfCard().setPT(1, 4);

        // Lifelink
        this.getLeftHalfCard().addAbility(LifelinkAbility.getInstance());

        // Extort
        this.getLeftHalfCard().addAbility(new ExtortAbility());

        // At the beginning of your postcombat main phase, if you gained 3 or more life this turn, exile Sorin of House Markov, then return him to the battlefield transformed under his owner's control.
        Ability ability = new BeginningOfPostcombatMainTriggeredAbility(
                new ExileAndReturnSourceEffect(PutCards.BATTLEFIELD_TRANSFORMED, Pronoun.HE), false
        ).withInterveningIf(condition).addHint(hint);
        ability.addWatcher(new PlayerGainedLifeWatcher());
        this.getLeftHalfCard().addAbility(ability);

        // Sorin, Ravenous Neonate
        this.getRightHalfCard().setStartingLoyalty(3);

        // Extort
        this.getRightHalfCard().addAbility(new ExtortAbility());

        // +2: Create a Food token.
        this.getRightHalfCard().addAbility(new LoyaltyAbility(new CreateTokenEffect(new FoodToken()), 2));

        // -1: Sorin, Ravenous Neonate deals damage equal to the amount of life you gained this turn to any target.
        ability = new LoyaltyAbility(
                new DamageTargetEffect(ControllerGainedLifeCount.instance)
                        .setText("{this} deals damage equal to the amount of life you gained this turn to any target"),
                -1
        );
        ability.addTarget(new TargetAnyTarget());
        ability.addWatcher(new PlayerGainedLifeWatcher());
        this.getRightHalfCard().addAbility(ability.addHint(ControllerGainedLifeCount.getHint()));

        // -6: Gain control of target creature. It becomes a Vampire in addition to its other types. Put a lifelink counter on it if you control a white permanent other than that creature or Sorin.
        Ability ability2 = new LoyaltyAbility(new GainControlTargetEffect(Duration.EndOfGame), -6);
        ability2.addTarget(new TargetCreaturePermanent());
        ability2.addEffect(new BecomesCreatureTypeTargetEffect(Duration.EndOfGame, SubType.VAMPIRE, false)
                .setText("It becomes a Vampire in addition to its other types"));
        ability2.addEffect(new ConditionalOneShotEffect(
                new AddCountersTargetEffect(CounterType.LIFELINK.createInstance()),
                conditionWhite, "Put a lifelink counter on it if you control a white permanent other than that creature or {this}"
        ));
        this.getRightHalfCard().addAbility(ability2.addHint(hintWhite));
    }

    private SorinOfHouseMarkov(final SorinOfHouseMarkov card) {
        super(card);
    }

    @Override
    public SorinOfHouseMarkov copy() {
        return new SorinOfHouseMarkov(this);
    }
}

enum SorinRavenousNeonatePredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        Ability source = input.getSource();
        Permanent permanent = input.getObject();
        return source != null
                && permanent != null
                && !permanent.getId().equals(source.getFirstTarget());
    }
}
