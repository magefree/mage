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
package mage.sets.shadowmoor;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.PersistAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author jonubuu
 */
public class MurderousRedcap extends CardImpl<MurderousRedcap> {

    public MurderousRedcap(UUID ownerId) {
        super(ownerId, 192, "Murderous Redcap", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{B/R}{B/R}");
        this.expansionSetCode = "SHM";
        this.subtype.add("Goblin");
        this.subtype.add("Assassin");

        this.color.setRed(true);
        this.color.setBlack(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Murderous Redcap enters the battlefield, it deals damage equal to its power to target creature or player.
        Ability ability = new EntersBattlefieldTriggeredAbility(new MurderousRedcapEffect());
        ability.addTarget(new TargetCreatureOrPlayer());
        this.addAbility(ability);
        // Persist
        this.addAbility(new PersistAbility());
    }

    public MurderousRedcap(final MurderousRedcap card) {
        super(card);
    }

    @Override
    public MurderousRedcap copy() {
        return new MurderousRedcap(this);
    }
}

class MurderousRedcapEffect extends OneShotEffect<MurderousRedcapEffect> {

    public MurderousRedcapEffect() {
        super(Constants.Outcome.Damage);
        staticText = "it deals damage equal to its power to target creature or player";
    }

    public MurderousRedcapEffect(final MurderousRedcapEffect effect) {
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
            permanent.damage(sourcePermanent.getPower().getValue(), sourcePermanent.getId(), game, true, false);
            return true;
        }
        Player player = game.getPlayer(source.getFirstTarget());
        if (sourcePermanent != null && player != null) {
            player.damage(sourcePermanent.getPower().getValue(), sourcePermanent.getId(), game, false, true);
            return true;
        }
        return false;
    }

    @Override
    public MurderousRedcapEffect copy() {
        return new MurderousRedcapEffect(this);
    }
}
