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

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 * Protection from a player is a new variant of the protection ability. It means the following:
 * -- True-Name Nemesis can’t be the target of spells or abilities controlled by the chosen player.
 * -- True-Name Nemesis can’t be enchanted by Auras or equipped by Equipment controlled
 *    by the chosen player. (The same is true for Fortifications controlled by the chosen player,
 *    if True-Name Nemesis becomes a land.)
 * -- True-Name Nemesis can’t be blocked by creatures controlled by the chosen player.
 * -- All damage that would be dealt to True-Name Nemesis by sources controlled by the chosen player
 *    is prevented. (The same is true for sources owned by the chosen player that don’t have controllers.)
 * @author LevelX2
 */
public class TrueNameNemesis extends CardImpl<TrueNameNemesis> {

    public TrueNameNemesis(UUID ownerId) {
        super(ownerId, 63, "True-Name Nemesis", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");
        this.expansionSetCode = "C13";
        this.subtype.add("Merfolk");
        this.subtype.add("Rogue");

        this.color.setBlue(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // As True-Name Nemesis enters the battlefield, choose a player.
        this.addAbility(new AsEntersBattlefieldAbility(new TrueNameNemesisChoosePlayerEffect()));
        // True-Name Nemesis has protection from the chosen player.
        this.addAbility(new ProtectionFromPlayerAbility());
    }

    public TrueNameNemesis(final TrueNameNemesis card) {
        super(card);
    }

    @Override
    public TrueNameNemesis copy() {
        return new TrueNameNemesis(this);
    }
}

class TrueNameNemesisChoosePlayerEffect extends OneShotEffect<TrueNameNemesisChoosePlayerEffect> {

    public TrueNameNemesisChoosePlayerEffect() {
        super(Outcome.Detriment);
        this.staticText = "choose a player";
    }

    public TrueNameNemesisChoosePlayerEffect(final TrueNameNemesisChoosePlayerEffect effect) {
        super(effect);
    }

    @Override
    public TrueNameNemesisChoosePlayerEffect copy() {
        return new TrueNameNemesisChoosePlayerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (player != null && permanent != null) {
            TargetPlayer target = new TargetPlayer(true);
            if (player.choose(this.outcome, target, source.getSourceId(), game)) {
                Player chosenPlayer = game.getPlayer(target.getFirstTarget());
                if (chosenPlayer != null) {
                    game.informPlayers(permanent.getName() + ": " + player.getName() + " has chosen " + chosenPlayer.getName());
                    game.getState().setValue(permanent.getId() + "_player", target.getFirstTarget());
                    permanent.addInfo("chosen player", "<i>Chosen player: " + chosenPlayer.getName() + "</i>");
                    return true;
                }
            }
        }
        return false;
    }
}

class ProtectionFromPlayerAbility extends ProtectionAbility {

    public ProtectionFromPlayerAbility() {
        super(new FilterCard());
    }

    public ProtectionFromPlayerAbility(final ProtectionFromPlayerAbility ability) {
        super(ability);
    }

    @Override
    public ProtectionFromPlayerAbility copy() {
        return new ProtectionFromPlayerAbility(this);
    }

    @Override
    public String getRule() {
        return "{this} has protection from the chosen player.";
    }

    @Override
    public boolean canTarget(MageObject source, Game game) {
        UUID playerId = (UUID) game.getState().getValue(this.getSourceId() + "_player");
        if (playerId != null && source != null) {
            if (source instanceof Permanent) {
                return !((Permanent) source).getControllerId().equals(playerId);
            }
            if (source instanceof Spell) {
                return !((Spell) source).getControllerId().equals(playerId);
            }
            if (source instanceof StackObject) {
                return !((StackObject) source).getControllerId().equals(playerId);
            }
        }
        return true;
    }
}
