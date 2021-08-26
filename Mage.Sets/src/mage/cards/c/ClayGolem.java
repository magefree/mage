package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesMonstrousSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.hint.common.MonstrousHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ClayGolem extends CardImpl {

    public ClayGolem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");

        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // {6}, Roll a d8: Monstrosity X, where X is the result.
        Ability ability = new SimpleActivatedAbility(new ClayGolemEffect(), new GenericManaCost(6));
        ability.addCost(new ClayGolemCost());
        this.addAbility(ability.addHint(MonstrousHint.instance));

        // Berserk — When Clay Golem becomes monstrous, destroy target permanent.
        ability = new BecomesMonstrousSourceTriggeredAbility(new DestroyTargetEffect());
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability.withFlavorWord("Berserk"));
    }

    private ClayGolem(final ClayGolem card) {
        super(card);
    }

    @Override
    public ClayGolem copy() {
        return new ClayGolem(this);
    }
}

class ClayGolemCost extends CostImpl {

    private int lastRoll = 0;

    ClayGolemCost() {
        super();
        text = "roll a d8";
    }

    private ClayGolemCost(final ClayGolemCost cost) {
        super(cost);
        this.lastRoll = cost.lastRoll;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return true;
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player player = game.getPlayer(controllerId);
        if (player == null) {
            return paid;
        }
        lastRoll = player.rollDice(Outcome.Benefit, source, game, 8);
        paid = true;
        return paid;
    }

    @Override
    public ClayGolemCost copy() {
        return new ClayGolemCost(this);
    }

    public int getLastRoll() {
        return lastRoll;
    }
}

class ClayGolemEffect extends OneShotEffect {
    ClayGolemEffect() {
        super(Outcome.Benefit);
        staticText = "monstrosity X, where X is the result";
    }

    private ClayGolemEffect(final ClayGolemEffect effect) {
        super(effect);
    }

    @Override
    public ClayGolemEffect copy() {
        return new ClayGolemEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null || permanent.isMonstrous()) {
            return false;
        }
        int monstrosityValue = source
                .getCosts()
                .stream()
                .filter(ClayGolemCost.class::isInstance)
                .map(ClayGolemCost.class::cast)
                .mapToInt(ClayGolemCost::getLastRoll)
                .findFirst()
                .orElse(0);
        permanent.addCounters(
                CounterType.P1P1.createInstance(monstrosityValue),
                source.getControllerId(), source, game
        );
        permanent.setMonstrous(true);
        game.fireEvent(GameEvent.getEvent(
                GameEvent.EventType.BECOMES_MONSTROUS, source.getSourceId(),
                source, source.getControllerId(), monstrosityValue
        ));
        return true;
    }
}
