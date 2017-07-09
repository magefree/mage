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
package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.DragonOnTheBattlefieldWhileSpellWasCastWatcher;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class DraconicRoar extends CardImpl {

    private static final FilterCard filter = new FilterCard("a Dragon card from your hand (you don't have to)");

    static {
        filter.add(new SubtypePredicate(SubType.DRAGON));
    }

    public DraconicRoar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{R}");

        // As an additional cost to cast Draconic Roar, you may reveal a Dragon card from your hand.
        this.getSpellAbility().addEffect(new InfoEffect("As an additional cost to cast {this}, you may reveal a Dragon card from your hand"));

        // Draconic Roar deals 3 damage to target creature. If you revealed a Dragon card or controlled a Dragon as you cast Draconic Roar, Draconic Roar deals 3 damage to that creature's controller.
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new DraconicRoarEffect());
        this.getSpellAbility().addWatcher(new DragonOnTheBattlefieldWhileSpellWasCastWatcher());
    }

    @Override
    public void adjustCosts(Ability ability, Game game) {
        if (ability.getAbilityType() == AbilityType.SPELL) {
            Player controller = game.getPlayer(ability.getControllerId());
            if (controller != null) {
                if (controller.getHand().count(filter, game) > 0) {
                    ability.addCost(new RevealTargetFromHandCost(new TargetCardInHand(0,1, filter)));
                }
            }
        }
    }

    public DraconicRoar(final DraconicRoar card) {
        super(card);
    }

    @Override
    public DraconicRoar copy() {
        return new DraconicRoar(this);
    }
}


class DraconicRoarEffect extends OneShotEffect {

    public DraconicRoarEffect() {
        super(Outcome.Benefit);
        this.staticText = "If you revealed a Dragon card or controlled a Dragon as you cast {this}, {this} deals 3 damage to that creature's controller";
    }

    public DraconicRoarEffect(final DraconicRoarEffect effect) {
        super(effect);
    }

    @Override
    public DraconicRoarEffect copy() {
        return new DraconicRoarEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            DragonOnTheBattlefieldWhileSpellWasCastWatcher watcher = (DragonOnTheBattlefieldWhileSpellWasCastWatcher) game.getState().getWatchers().get(DragonOnTheBattlefieldWhileSpellWasCastWatcher.class.getSimpleName());
            if (watcher != null && watcher.castWithConditionTrue(source.getId())) {
                Permanent permanent = game.getPermanentOrLKIBattlefield(getTargetPointer().getFirst(game, source));
                if (permanent != null) {
                    Player player = game.getPlayer(permanent.getControllerId());
                    if (player != null) {
                        player.damage(3, source.getSourceId(), game, false, true);
                    }
                }
            }
            return true;
        }
        return false;
    }
}

