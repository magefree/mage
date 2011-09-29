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

package mage.sets.mirrodinbesieged;

import java.util.UUID;

import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.BattleCryAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SoldierToken;
import mage.players.Player;

/**
 * @author Loki
 */
public class HeroOfBladehold extends CardImpl<HeroOfBladehold> {

    public HeroOfBladehold(UUID ownerId) {
        super(ownerId, 8, "Hero of Bladehold", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");
        this.expansionSetCode = "MBS";
        this.subtype.add("Human");
        this.subtype.add("Knight");
        this.color.setWhite(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);
        this.addAbility(new BattleCryAbility());
        this.addAbility(new AttacksTriggeredAbility(new HeroOfBladeholdEffect(), false));
    }

    public HeroOfBladehold(final HeroOfBladehold card) {
        super(card);
    }

    @Override
    public HeroOfBladehold copy() {
        return new HeroOfBladehold(this);
    }

}

class HeroOfBladeholdEffect extends OneShotEffect<HeroOfBladeholdEffect> {
    HeroOfBladeholdEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "put two 1/1 white Soldier creature tokens onto the battlefield tapped and attacking";
    }

    HeroOfBladeholdEffect(final HeroOfBladeholdEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        SoldierToken token = new SoldierToken();
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            token.putOntoBattlefield(2, game, source.getId(), source.getControllerId());
            Permanent p = game.getPermanent(token.getLastAddedToken());
            game.getCombat().declareAttacker(p.getId(), game.getCombat().getDefendingPlayer(source.getSourceId()), game);
            p.setTapped(true);
        }
        return true;
    }

    @Override
    public HeroOfBladeholdEffect copy() {
        return new HeroOfBladeholdEffect(this);
    }

}