package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TapSourceUnlessPaysEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByAllTargetEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPlayer;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class TheBlackGate extends CardImpl {

    public TheBlackGate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GATE);

        // As The Black Gate enters the battlefield, you may pay 3 life. If you don't, it enters the battlefield tapped.
        this.addAbility(new AsEntersBattlefieldAbility(
                new TapSourceUnlessPaysEffect(new PayLifeCost(3)),
                "you may pay 3 life. If you don't, it enters the battlefield tapped"
        ));

        // {T}: Add {B}.
        this.addAbility(new BlackManaAbility());

        // {1}{B}, {T}: Choose a player with the most life or tied for most life. Target creature can't be blocked by creatures that player controls this turn.
        ActivatedAbility ability = new SimpleActivatedAbility(
                new BlackGateEffect(),
                new ManaCostsImpl("{1}{B}")
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private TheBlackGate(final TheBlackGate card) {
        super(card);
    }

    @Override
    public TheBlackGate copy() {
        return new TheBlackGate(this);
    }
}

enum BlackGatePredicate implements ObjectSourcePlayerPredicate<Player> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Player> input, Game game) {
        Player player = input.getObject();
        UUID playerId = input.getPlayerId();
        if (player == null || playerId == null) {
            return false;
        }

        int life = player.getLife();
        for (UUID otherPlayerId : game.getState().getPlayersInRange(playerId, game)) {
            Player otherPlayer = game.getPlayer(otherPlayerId);
            if (otherPlayer == null) {
                continue;
            }
            if (life < otherPlayer.getLife()) {
                return false;
            }
        }
        return true;
    }
}

class BlackGateEffect extends OneShotEffect {

    private static final FilterPlayer filter = new FilterPlayer("player with the most life or tied for most life");

    static {
        filter.add(BlackGatePredicate.instance);
    }

    BlackGateEffect() {
        super(Outcome.Benefit);
        staticText = "Choose a player with the most life or tied for most life. "
                + "Target creature can't be blocked by creatures that player controls this turn.";
    }

    private BlackGateEffect(final BlackGateEffect effect) {
        super(effect);
    }

    @Override
    public BlackGateEffect copy() {
        return new BlackGateEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent creature = game.getPermanent(source.getFirstTarget());
        if (controller == null || creature == null) {
            return false;
        }

        TargetPlayer target = new TargetPlayer(filter);
        target.setNotTarget(true);
        if (!controller.choose(Outcome.Detriment, target, source, game)) {
            return false;
        }

        Player player = game.getPlayer(target.getFirstTarget());
        if (player == null) {
            return false;
        }

        game.informPlayers(controller.getLogName() + " chose " + player.getLogName() + CardUtil.getSourceLogName(game, source));

        FilterCreaturePermanent filterCantBlock = new FilterCreaturePermanent("creatures controlled by" + player.getName());
        filterCantBlock.add(new ControllerIdPredicate(player.getId()));

        ContinuousEffect effect = new CantBeBlockedByAllTargetEffect(filterCantBlock, Duration.EndOfTurn);
        effect.setTargetPointer(new FixedTarget(creature, game));
        game.addEffect(effect, source);

        return true;
    }
}