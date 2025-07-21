package mage.cards.b;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.ControllerGainedLifeCount;
import mage.abilities.dynamicvalue.common.ControllerLostLifeCount;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInGraveyard;
import mage.target.targetpointer.SecondTargetPointer;
import mage.watchers.common.PlayerGainedLifeWatcher;

import java.util.UUID;

/**
 * @author Grath
 */
public final class BetorAncestorsVoice extends CardImpl {

    final private static FilterCard filter
            = new FilterCard("creature card with mana value less than or equal to the amount of life you lost this turn");

    static {
        filter.add(CardType.CREATURE.getPredicate());
        filter.add(BetorPredicate.instance);
    }

    public BetorAncestorsVoice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // At the beginning of your end step, put a number of +1/+1 counters on up to one other target creature you
        // control equal to the amount of life you gained this turn. Return up to one target creature card with mana
        // value less than or equal to the amount of life you lost this turn from your graveyard to the battlefield.
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance(), ControllerGainedLifeCount.instance)
                        .setText("put a number of +1/+1 counters on up to one other target creature you " +
                                "control equal to the amount of life you gained this turn.")
        );
        ability.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_ANOTHER_TARGET_CREATURE_YOU_CONTROL));
        ability.addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect().setTargetPointer(new SecondTargetPointer())
                .setText("Return up to one target creature card with mana value less than or equal to the amount of " +
                        "life you lost this turn from your graveyard to the battlefield."));
        ability.addTarget(new TargetCardInGraveyard(0, 1, filter));
        ability.addHint(ControllerGainedLifeCount.getHint());
        ability.addHint(ControllerLostLifeCount.getHint());
        this.addAbility(ability, new PlayerGainedLifeWatcher());
    }

    private BetorAncestorsVoice(final BetorAncestorsVoice card) {
        super(card);
    }

    @Override
    public BetorAncestorsVoice copy() {
        return new BetorAncestorsVoice(this);
    }
}

enum BetorPredicate implements ObjectSourcePlayerPredicate<MageObject> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<MageObject> input, Game game) {
        return input.getObject().getManaValue() <= ControllerLostLifeCount.instance.calculate(game, input.getSource(), null);
    }

    @Override
    public String toString() {
        return "mana value less than or equal to the amount of life you lost this turn";
    }
}
