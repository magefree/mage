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
package mage.sets.mirrodin;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author Loki
 */
public class SpikeshotGoblin extends CardImpl<SpikeshotGoblin> {

    public SpikeshotGoblin(UUID ownerId) {
        super(ownerId, 108, "Spikeshot Goblin", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.expansionSetCode = "MRD";
        this.subtype.add("Goblin");
        this.subtype.add("Shaman");

        this.color.setRed(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {R}, {tap}: Spikeshot Goblin deals damage equal to its power to target creature or player.
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new SpikeshotGoblinEffect(), new ColoredManaCost(Constants.ColoredManaSymbol.R));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreatureOrPlayer());
        this.addAbility(ability);
    }

    public SpikeshotGoblin(final SpikeshotGoblin card) {
        super(card);
    }

    @Override
    public SpikeshotGoblin copy() {
        return new SpikeshotGoblin(this);
    }
}

class SpikeshotGoblinEffect extends OneShotEffect<SpikeshotGoblinEffect> {
    public SpikeshotGoblinEffect() {
        super(Constants.Outcome.Damage);
        staticText = "{this} deals damage equal to its power to target creature or player";
    }

    public SpikeshotGoblinEffect(final SpikeshotGoblinEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (sourcePermanent == null) {
            sourcePermanent = (Permanent) game.getLastKnownInformation(source.getSourceId(), Constants.Zone.BATTLEFIELD);
        }
        if (sourcePermanent != null && permanent != null) {
            permanent.damage(sourcePermanent.getPower().getValue(), source.getId(), game, true, false);
            return true;
        }
        Player player = game.getPlayer(source.getFirstTarget());
        if (sourcePermanent != null && player != null) {
            player.damage(sourcePermanent.getPower().getValue(), source.getSourceId(), game, false, true);
            return true;
        }
        return false;
    }

    @Override
    public SpikeshotGoblinEffect copy() {
        return new SpikeshotGoblinEffect(this);
    }

}
