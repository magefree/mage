package mage.cards.t;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.combat.GoadTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.SkulkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterOpponentsCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheMasterMesmerist extends CardImpl {

    private static final FilterPermanent filter = new FilterOpponentsCreaturePermanent("creature an opponent controls with power less than or equal to {this}'s power");
    private static final FilterPermanent filter2 = new FilterCreaturePermanent();

    static {
        filter.add(TheMasterMesmeristPredicate.instance);
        filter2.add(new AbilityPredicate(SkulkAbility.class));
    }

    public TheMasterMesmerist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TIME_LORD);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {T}: Target creature an opponent controls with power less than or equal to The Master's power gains skulk until end of turn. Goad it.
        Ability ability = new SimpleActivatedAbility(new GainAbilityTargetEffect(new SkulkAbility()), new TapSourceCost());
        ability.addEffect(new GoadTargetEffect().setText("goad it"));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // Whenever a creature with skulk deals combat damage to one of your opponents, put a +1/+1 counter on The Master and draw a card.
        ability = new DealsDamageToAPlayerAllTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), filter2, false,
                SetTargetPointer.NONE, true, false, TargetController.OPPONENT
        ).setTriggerPhrase("Whenever a creature with skulk deals combat damage to one of your opponents, ");
        ability.addEffect(new DrawCardSourceControllerEffect(1).concatBy("and"));
        this.addAbility(ability);
    }

    private TheMasterMesmerist(final TheMasterMesmerist card) {
        super(card);
    }

    @Override
    public TheMasterMesmerist copy() {
        return new TheMasterMesmerist(this);
    }
}

enum TheMasterMesmeristPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        return Optional
                .ofNullable(input.getSource().getSourcePermanentOrLKI(game))
                .map(MageObject::getPower)
                .map(MageInt::getValue)
                .map(p -> input.getObject().getPower().getValue() <= p)
                .orElse(false);
    }
}
