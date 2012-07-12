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
package mage.sets.magic2013;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public class VoidStalker extends CardImpl<VoidStalker> {

    public VoidStalker(UUID ownerId) {
        super(ownerId, 77, "Void Stalker", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.expansionSetCode = "M13";
        this.subtype.add("Elemental");

        this.color.setBlue(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {2}{U}, {tap}: Put Void Stalker and target creature on top of their owners' libraries, then those players shuffle their libraries.
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new VoidStalkerEffect(), new ManaCostsImpl("{2}{U}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public VoidStalker(final VoidStalker card) {
        super(card);
    }

    @Override
    public VoidStalker copy() {
        return new VoidStalker(this);
    }
}

class VoidStalkerEffect extends OneShotEffect<VoidStalkerEffect> {
    VoidStalkerEffect() {
        super(Constants.Outcome.ReturnToHand);
        staticText = "Put {this} and target creature on top of their owners' libraries, then those players shuffle their libraries";
    }

    VoidStalkerEffect(final VoidStalkerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent p = game.getPermanent(targetPointer.getFirst(game, source));
        Permanent s = game.getPermanent(source.getSourceId());
        if (p != null) {
            p.moveToZone(Constants.Zone.LIBRARY, source.getSourceId(), game, true);
            Player pl = game.getPlayer(p.getControllerId());
            if (pl != null)
                pl.shuffleLibrary(game);
        }
        if (s != null) {
            s.moveToZone(Constants.Zone.LIBRARY, source.getSourceId(), game, true);
            Player pl = game.getPlayer(s.getControllerId());
            if (pl != null)
                pl.shuffleLibrary(game);
        }
        return true;
    }

    @Override
    public VoidStalkerEffect copy() {
        return new VoidStalkerEffect(this);
    }
}