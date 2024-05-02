package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.OneOrMoreMilledTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SavedMilledValue;
import mage.abilities.effects.common.counter.AddCountersPlayersEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheWiseMothman extends CardImpl {

    public TheWiseMothman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever The Wise Mothman enters the battlefield or attacks, each player gets a rad counter.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(
                new AddCountersPlayersEffect(CounterType.RAD.createInstance(), TargetController.EACH_PLAYER)
        ));

        // Whenever one or more nonland cards are milled, put a +1/+1 counter on each of up to X target creatures, where X is the number of nonland cards milled this way.
        Ability ability = new OneOrMoreMilledTriggeredAbility(
                StaticFilters.FILTER_CARDS_NON_LAND,
                new AddCountersTargetEffect(CounterType.P1P1.createInstance())
                        .setText("put a +1/+1 counter on each of up to X target creatures, "
                                + "where X is the number of nonland cards milled this way")
        );
        ability.addTarget(new TargetCreaturePermanent(0, 0));
        ability.setTargetAdjuster(new TheWiseMothmanAdjuster(SavedMilledValue.MUCH));
        this.addAbility(ability);
    }

    private TheWiseMothman(final TheWiseMothman card) {
        super(card);
    }

    @Override
    public TheWiseMothman copy() {
        return new TheWiseMothman(this);
    }
}

// TODO: cleanup after #12107
class TheWiseMothmanAdjuster implements TargetAdjuster {
    private final DynamicValue dynamicValue;

    TheWiseMothmanAdjuster(DynamicValue value) {
        this.dynamicValue = value;
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        int count = dynamicValue.calculate(game, ability, ability.getEffects().get(0));
        ability.getTargets().clear();
        if (count > 0) {
            ability.addTarget(new TargetCreaturePermanent(0, count));
        }
    }
}