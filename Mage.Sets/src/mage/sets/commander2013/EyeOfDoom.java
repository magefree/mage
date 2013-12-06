/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.sets.commander2013;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
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

/**
 *
 * @author LevelX2
 */
public class EyeOfDoom extends CardImpl<EyeOfDoom> {

    private static final FilterPermanent filter = new FilterPermanent("permanent with a doom counter on it");
    static {
        filter.add(new CounterPredicate(CounterType.DOOM));
    }
    public EyeOfDoom(UUID ownerId) {
        super(ownerId, 243, "Eye of Doom", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{4}");
        this.expansionSetCode = "C13";

        // When Eye of Doom enters the battlefield, each player chooses a nonland permanent and puts a doom counter on it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new EyeOfDoomEffect(),false));

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

class EyeOfDoomEffect extends OneShotEffect<EyeOfDoomEffect> {

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
        List<Permanent> permanents = new ArrayList<Permanent>();
        Target target = new TargetNonlandPermanent();
        target.setRequired(true);
        target.setNotTarget(false);
        PlayerList playerList = game.getPlayerList().copy();
        playerList.setCurrent(game.getActivePlayerId());
        Player player = game.getPlayer(game.getActivePlayerId());
        do {
            target.clearChosen();
            if (player.chooseTarget(outcome, target, source, game)) {
                Permanent permanent = game.getPermanent(target.getFirstTarget());
                if (permanent != null) {
                    permanents.add(permanent);
                    game.informPlayers((new StringBuilder(player.getName()).append(" chooses ").append(permanent.getName()).toString()));
                }
            }
            player = playerList.getNext(game);
        } while (!player.getId().equals(game.getActivePlayerId()));

        for (Permanent permanent: permanents) {
            permanent.addCounters(CounterType.DOOM.createInstance(), game);
        }

        return true;
    }
}
