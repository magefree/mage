package mage.cards.s;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.ControllerGainedLifeCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTypeTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.ExtortAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
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
public final class SorinRavenousNeonate extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("white permanent other than that creature or {this}");
    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter, true);
    private static final Hint hint = new ConditionHint(condition, "you control another white permanent");

    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
        filter.add(AnotherPredicate.instance);
        filter.add(SorinRavenousNeonatePredicate.instance);
    }

    public SorinRavenousNeonate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SORIN);
        this.setStartingLoyalty(3);

        this.color.setWhite(true);
        this.color.setBlack(true);
        this.nightCard = true;

        // Extort
        this.addAbility(new ExtortAbility());

        // +2: Create a Food token.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new FoodToken()), 2));

        // -1: Sorin, Ravenous Neonate deals damage equal to the amount of life you gained this turn to any target.
        Ability ability = new LoyaltyAbility(new DamageTargetEffect(ControllerGainedLifeCount.instance), -1);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability.addHint(ControllerGainedLifeCount.getHint()), new PlayerGainedLifeWatcher());

        // -6: Gain control of target creature. It becomes a Vampire in addition to its other types. Put a lifelink counter on it if you control a white permanent other than that creature or Sorin.
        ability = new LoyaltyAbility(new GainControlTargetEffect(Duration.EndOfGame), -6);
        ability.addTarget(new TargetCreaturePermanent());
        ability.addEffect(new BecomesCreatureTypeTargetEffect(Duration.EndOfGame, SubType.VAMPIRE, false)
                .setText("It becomes a Vampire in addition to its other types"));
        ability.addEffect(new ConditionalOneShotEffect(
                new AddCountersTargetEffect(CounterType.LIFELINK.createInstance()),
                condition, "Put a lifelink counter on it if you control a white permanent other than that creature or {this}"
        ));
        this.addAbility(ability.addHint(hint));
    }

    private SorinRavenousNeonate(final SorinRavenousNeonate card) {
        super(card);
    }

    @Override
    public SorinRavenousNeonate copy() {
        return new SorinRavenousNeonate(this);
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