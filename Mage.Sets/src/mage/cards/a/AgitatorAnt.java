package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.GoadTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class AgitatorAnt extends CardImpl {

    public AgitatorAnt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // At the beginning of your end step, each player may put two +1/+1 counters on a creature they control. Goad each creature that had counters put on it this way.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new AgitatorAntEffect(), TargetController.YOU, false
        ));
    }

    private AgitatorAnt(final AgitatorAnt card) {
        super(card);
    }

    @Override
    public AgitatorAnt copy() {
        return new AgitatorAnt(this);
    }
}

class AgitatorAntEffect extends OneShotEffect {

    AgitatorAntEffect() {
        super(Outcome.Benefit);
        staticText = "each player may put two +1/+1 counters on a creature they control. " +
                "Goad each creature that had counters put on it this way.";
    }

    private AgitatorAntEffect(final AgitatorAntEffect effect) {
        super(effect);
    }

    @Override
    public AgitatorAntEffect copy() {
        return new AgitatorAntEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Player player : game
                .getState()
                .getPlayersInRange(source.getControllerId(), game)
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull).collect(Collectors.toList())) {
            if (game.getBattlefield().countAll(
                    StaticFilters.FILTER_PERMANENT_CREATURE, player.getId(), game
            ) < 1 || !player.chooseUse(
                    Outcome.BoostCreature, "Put two +1/+1 counters on a creature you control?", source, game
            )) {
                continue;
            }
            TargetPermanent targetPermanent = new TargetControlledCreaturePermanent(0, 1);
            targetPermanent.setNotTarget(true);
            player.choose(Outcome.BoostCreature, targetPermanent, source, game);
            Permanent permanent = game.getPermanent(targetPermanent.getFirstTarget());
            if (permanent == null || !permanent.addCounters(CounterType.P1P1.createInstance(2), player.getId(), source, game)) {
                continue;
            }
            game.addEffect(new GoadTargetEffect().setTargetPointer(new FixedTarget(permanent, game)), source);
        }
        return true;
    }
}
