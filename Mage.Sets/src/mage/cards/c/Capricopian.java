package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPlayer;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.other.PlayerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Capricopian extends CardImpl {

    public Capricopian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{G}");

        this.subtype.add(SubType.GOAT);
        this.subtype.add(SubType.HYDRA);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Capricopian enters the battlefield with X +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(
                new EntersBattlefieldWithXCountersEffect(CounterType.P1P1.createInstance())
        ));

        // {2}: Put a +1/+1 counter on Capricopian, then you may reselect which player Capricopian is attacking. Only the player Capricopian is attacking may activate this ability and only during the declare attackers step.
        this.addAbility(new CapricopianActivatedAbility());
    }

    private Capricopian(final Capricopian card) {
        super(card);
    }

    @Override
    public Capricopian copy() {
        return new Capricopian(this);
    }
}

class CapricopianActivatedAbility extends ActivatedAbilityImpl {

    CapricopianActivatedAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()), new GenericManaCost(2));
        this.addEffect(new CapricopianEffect());
        this.setMayActivate(TargetController.ANY);
        this.condition = new IsStepCondition(PhaseStep.DECLARE_ATTACKERS);
    }

    private CapricopianActivatedAbility(final CapricopianActivatedAbility ability) {
        super(ability);
    }

    @Override
    public CapricopianActivatedAbility copy() {
        return new CapricopianActivatedAbility(this);
    }

    @Override
    public String getRule() {
        return "{2}: Put a +1/+1 counter on {this}, then you may reselect which player {this} is attacking. " +
                "Only the player {this} is attacking may activate this ability " +
                "and only during the declare attackers step. <i>(It can't attack its controller.)</i>";
    }

    @Override
    protected boolean checkTargetController(UUID playerId, Game game) {
        return super.checkTargetController(playerId, game)
                && playerId != null
                && playerId.equals(game.getCombat().getDefenderId(this.getSourceId()));
    }
}

class CapricopianEffect extends OneShotEffect {

    CapricopianEffect() {
        super(Outcome.Benefit);
    }

    private CapricopianEffect(final CapricopianEffect effect) {
        super(effect);
    }

    @Override
    public CapricopianEffect copy() {
        return new CapricopianEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (player == null || permanent == null) {
            return false;
        }
        if (!player.chooseUse(outcome, "Reselect attacker for " + permanent.getIdName() + "?", source, game)) {
            return false;
        }
        FilterPlayer filterPlayer = new FilterPlayer();
        filterPlayer.add(Predicates.not(new PlayerIdPredicate(permanent.getControllerId())));
        filterPlayer.add(Predicates.not(new PlayerIdPredicate(game.getCombat().getDefenderId(permanent.getId()))));
        TargetPlayer targetPlayer = new TargetPlayer(0, 1, true, filterPlayer);
        player.choose(outcome, targetPlayer, source, game);
        Player newPlayer = game.getPlayer(targetPlayer.getFirstTarget());
        if (newPlayer == null) {
            return false;
        }
        game.getCombat().removeAttacker(permanent.getId(), game);
        return game.getCombat().addAttackingCreature(permanent.getId(), game, newPlayer.getId());
    }
}
