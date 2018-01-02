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
package mage.cards.t;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author L_J
 */
public class TidalFlats extends CardImpl {

    public TidalFlats(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{U}");
        
        // {U}{U}: For each attacking creature without flying, its controller may pay {1}. If he or she doesn't, creatures you control blocking that creature gain first strike until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new TidalFlatsEffect(), new ManaCostsImpl("{U}{U}")));
    }

    public TidalFlats(final TidalFlats card) {
        super(card);
    }

    @Override
    public TidalFlats copy() {
        return new TidalFlats(this);
    }
}

class TidalFlatsEffect extends OneShotEffect {
    
    private static final FilterAttackingCreature filter = new FilterAttackingCreature("attacking creature without flying");
    static {
        filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public TidalFlatsEffect() {
        super(Outcome.Benefit);
        this.staticText = "For each attacking creature without flying, its controller may pay {1}. If he or she doesn't, creatures you control blocking that creature gain first strike until end of turn";
    }

    public TidalFlatsEffect(final TidalFlatsEffect effect) {
        super(effect);
    }

    @Override
    public TidalFlatsEffect copy() {
        return new TidalFlatsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.getPlayerList();
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Player player = game.getPlayer(game.getActivePlayerId());
            Cost cost = new ManaCostsImpl("{1}");            
            List<Permanent> affectedPermanents = new ArrayList<>();
            for (Permanent permanent : game.getState().getBattlefield().getAllActivePermanents(filter, player.getId(), game)) {
                cost.clearPaid();
                String message = "Pay " + cost.getText() + " for " + permanent.getLogName() + "? If you don't, creatures " + controller.getLogName() + " controls blocking it gain first strike until end of turn.";
                if (player != null && player.chooseUse(Outcome.Benefit, message, source, game)) {
                    if (cost.pay(source, game, source.getSourceId(), player.getId(), false, null)) {
                        game.informPlayers(player.getLogName() + " paid " + cost.getText() + " for " + permanent.getLogName());
                        continue;
                    } else {
                        game.informPlayers(player.getLogName() + " didn't pay " + cost.getText() + " for " + permanent.getLogName());
                        affectedPermanents.add(permanent);
                    }
                } else {
                    game.informPlayers(player.getLogName() + " didn't pay " + cost.getText() + " for " + permanent.getLogName());
                    affectedPermanents.add(permanent);
                }
            }

            for (Permanent permanent : affectedPermanents) {
                CombatGroup group = game.getCombat().findGroup(permanent.getId());
                if (group != null) {
                    for (UUID blockerId : group.getBlockers()) {
                        Permanent blocker = game.getPermanent(blockerId);
                        if (blocker != null && blocker.getControllerId() == controller.getId()) {
                            ContinuousEffect effect = new GainAbilityTargetEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn);
                            effect.setTargetPointer(new FixedTarget(blocker.getId()));
                            game.addEffect(effect, source);
                        }
                    }
                }
                
            }
            return true;
        }
        return false;
    }
}
