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
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.FilterArtifactPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author L_J
 */
public class Corrosion extends CardImpl {

    public Corrosion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{B}{R}");

        // Cumulative upkeep-Pay {1}.
        this.addAbility(new CumulativeUpkeepAbility(new GenericManaCost(1)));

        // At the beginning of your upkeep, put a rust counter on each artifact target opponent controls. Then destroy each artifact with converted mana cost less than or equal to the number of rust counters on it. Artifacts destroyed this way can't be regenerated.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new CorrosionUpkeepEffect(), TargetController.YOU, false);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
        
        // When Corrosion leaves the battlefield, remove all rust counters from all permanents.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new CorrosionRemoveCountersEffect(), false)); 
    }

    public Corrosion(final Corrosion card) {
        super(card);
    }

    @Override
    public Corrosion copy() {
        return new Corrosion(this);
    }
}

class CorrosionUpkeepEffect extends OneShotEffect {
    
    private static final FilterArtifactPermanent filter = new FilterArtifactPermanent();
    
    CorrosionUpkeepEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "put a rust counter on each artifact target opponent controls. Then destroy each artifact with converted mana cost less than or equal to the number of rust counters on it. Artifacts destroyed this way can't be regenerated";
    }
    
    CorrosionUpkeepEffect(final CorrosionUpkeepEffect effect) {
        super(effect);
    }
    
    @Override
    public CorrosionUpkeepEffect copy() {
        return new CorrosionUpkeepEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (player != null && sourcePermanent != null) {
            Player targetPlayer = game.getPlayer(source.getFirstTarget());
            // put a rust counter on each artifact target opponent controls
            if (targetPlayer != null) {
                for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, targetPlayer.getId(), game)) {
                    permanent.addCounters(CounterType.RUST.createInstance(), source, game);
                }
            }
            // destroy each artifact with converted mana cost less than or equal to the number of rust counters on it
            for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
                if (permanent.getConvertedManaCost() <= permanent.getCounters(game).getCount(CounterType.RUST)) {
                    permanent.destroy(source.getSourceId(), game, true);
                }
            }
            return true;
        }
        return false;
    }
}

class CorrosionRemoveCountersEffect extends OneShotEffect {

    public CorrosionRemoveCountersEffect() {
        super(Outcome.Neutral);
        staticText = "remove all rust counters from all permanents";
    }

    public CorrosionRemoveCountersEffect(final CorrosionRemoveCountersEffect effect) {
        super(effect);
    }

    @Override
    public CorrosionRemoveCountersEffect copy() {
        return new CorrosionRemoveCountersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents()) {
            permanent.removeCounters(CounterType.RUST.createInstance(permanent.getCounters(game).getCount(CounterType.RUST)), game);
        }
        return true;
    }
}
