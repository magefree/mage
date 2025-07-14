package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.dynamicvalue.common.ManaSpentToCastCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.token.RobotToken;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DyadrineSynthesisAmalgam extends CardImpl {

    public DyadrineSynthesisAmalgam(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{X}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Dyadrine enters with a number of +1/+1 counters on it equal to the amount of mana spent to cast it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(
                CounterType.P1P1.createInstance(), ManaSpentToCastCount.instance, true
        ), "with a number of +1/+1 counters on it equal to the amount of mana spent to cast it"));

        // Whenever you attack, you may remove a +1/+1 counter from each of two creatures you control. If you do, draw a card and create a 2/2 colorless Robot artifact creature token.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(new DoIfCostPaid(
                new DrawCardSourceControllerEffect(1), new DyadrineSynthesisAmalgamCost()
        ).addEffect(new CreateTokenEffect(new RobotToken()).concatBy("and")), 1));
    }

    private DyadrineSynthesisAmalgam(final DyadrineSynthesisAmalgam card) {
        super(card);
    }

    @Override
    public DyadrineSynthesisAmalgam copy() {
        return new DyadrineSynthesisAmalgam(this);
    }
}

class DyadrineSynthesisAmalgamCost extends CostImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(CounterType.P1P1.getPredicate());
    }

    DyadrineSynthesisAmalgamCost() {
        super();
        setText("remove a +1/+1 counter from each of two creatures you control");
    }

    private DyadrineSynthesisAmalgamCost(final DyadrineSynthesisAmalgamCost cost) {
        super(cost);
    }

    @Override
    public DyadrineSynthesisAmalgamCost copy() {
        return new DyadrineSynthesisAmalgamCost(this);
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return game.getBattlefield().contains(filter, controllerId, source, game, 2);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player player = game.getPlayer(controllerId);
        if (player == null) {
            return paid;
        }
        TargetPermanent target = new TargetPermanent(2, filter);
        target.withNotTarget(true);
        target.withChooseHint("to remove a +1/+1 counter from");
        player.choose(Outcome.UnboostCreature, target, source, game);
        for (UUID permanentId : target.getTargets()) {
            Optional.ofNullable(permanentId)
                    .map(game::getPermanent)
                    .ifPresent(permanent -> permanent.removeCounters(CounterType.P1P1.createInstance(), source, game));
        }
        paid = true;
        return paid;
    }
}
