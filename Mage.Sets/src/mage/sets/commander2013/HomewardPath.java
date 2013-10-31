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

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class HomewardPath extends CardImpl<HomewardPath> {

    public HomewardPath(UUID ownerId) {
        super(ownerId, 295, "Homeward Path", Rarity.RARE, new CardType[]{CardType.LAND}, "");
        this.expansionSetCode = "C13";

        // {tap}: Add {1} to your mana pool.
        this.addAbility(new ColorlessManaAbility());
        // {tap}: Each player gains control of all creatures he or she owns.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new HomewardPathEffect(), new TapSourceCost()));

    }

    public HomewardPath(final HomewardPath card) {
        super(card);
    }

    @Override
    public HomewardPath copy() {
        return new HomewardPath(this);
    }
}

class HomewardPathEffect extends OneShotEffect<HomewardPathEffect> {

    public HomewardPathEffect() {
        super(Outcome.Detriment);
        this.staticText = "Each player gains control of all creatures he or she owns";
    }

    public HomewardPathEffect(final HomewardPathEffect effect) {
        super(effect);
    }

    @Override
    public HomewardPathEffect copy() {
        return new HomewardPathEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {

        }
        return false;
    }
}

class HomewardPathControlEffect extends ContinuousEffectImpl<HomewardPathControlEffect> {

    public HomewardPathControlEffect() {
        super(Duration.EndOfGame, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
        this.staticText = "Each player gains control of all creatures he or she owns";
    }

    public HomewardPathControlEffect(final HomewardPathControlEffect effect) {
        super(effect);
    }

    @Override
    public HomewardPathControlEffect copy() {
        return new HomewardPathControlEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        // add all creatures in range
        for (Permanent permanent :game.getBattlefield().getActivePermanents(new FilterCreaturePermanent(), source.getControllerId(), game)) {
            objects.add(permanent.getId());
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Set<UUID> toRemove = new HashSet<UUID>();
        for (UUID creatureId :objects) {
            Permanent creature = game.getPermanent(creatureId);
            if (creature != null) {
                creature.changeControllerId(creature.getOwnerId(), game);
            } else {
                toRemove.add(creatureId);
            }
        }
        objects.removeAll(toRemove);
        if (objects.isEmpty()) {
            this.discard();
        }
        return true;
    }
}
