package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.CounterPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.players.PlayerList;
import mage.target.Target;
import mage.target.common.TargetNonlandPermanent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class EyeOfDoom extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("permanent with a doom counter on it");

    static {
        filter.add(new CounterPredicate(CounterType.DOOM));
    }

    public EyeOfDoom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // When Eye of Doom enters the battlefield, each player chooses a nonland permanent and puts a doom counter on it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new EyeOfDoomEffect(), false));

        // {2}, {tap}, Sacrifice Eye of Doom: Destroy each permanent with a doom counter on it.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyAllEffect(filter), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    public EyeOfDoom(final EyeOfDoom card) {
        super(card);
    }

    @Override
    public EyeOfDoom copy() {
        return new EyeOfDoom(this);
    }
}

class EyeOfDoomEffect extends OneShotEffect {

    public EyeOfDoomEffect() {
        super(Outcome.Detriment);
        this.staticText = "each player chooses a nonland permanent and puts a doom counter on it";
    }

    public EyeOfDoomEffect(final EyeOfDoomEffect effect) {
        super(effect);
    }

    @Override
    public EyeOfDoomEffect copy() {
        return new EyeOfDoomEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = new ArrayList<>();
        Target target = new TargetNonlandPermanent();
        target.setNotTarget(false);
        PlayerList playerList = game.getPlayerList().copy();
        playerList.setCurrent(game.getActivePlayerId());
        Player player = game.getPlayer(game.getActivePlayerId());
        do {
            target.clearChosen();
            if (player != null && player.chooseTarget(outcome, target, source, game)) {
                Permanent permanent = game.getPermanent(target.getFirstTarget());
                if (permanent != null) {
                    permanents.add(permanent);
                    game.informPlayers(player.getLogName() + " chooses " + permanent.getName());
                }
            }
            player = playerList.getNext(game, false);
        } while (player != null && !player.getId().equals(game.getActivePlayerId()));

        for (Permanent permanent : permanents) {
            permanent.addCounters(CounterType.DOOM.createInstance(), source, game);
        }

        return true;
    }
}
