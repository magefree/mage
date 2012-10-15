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
package mage.sets.returntoravnica;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SpellCastTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.choices.ChoiceColor;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class TabletOfTheGuilds extends CardImpl<TabletOfTheGuilds> {

    public TabletOfTheGuilds(UUID ownerId) {
        super(ownerId, 235, "Tablet of the Guilds", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT}, "{2}");
        this.expansionSetCode = "RTR";

        // As Tablet of the Guilds enters the battlefield, choose two colors.
        this.addAbility(new AsEntersBattlefieldAbility(new TabletOfTheGuildsEntersBattlefieldEffect()));

        // Whenever you cast a spell, if it's at least one of the chosen colors, you gain 1 life for each of the chosen colors it is.
       this.addAbility(new SpellCastTriggeredAbility(new TabletOfTheGuildsGainLifeEffect(), new FilterSpell("a spell"), false, true ));
    }

    public TabletOfTheGuilds(final TabletOfTheGuilds card) {
        super(card);
    }

    @Override
    public TabletOfTheGuilds copy() {
        return new TabletOfTheGuilds(this);
    }
}

class TabletOfTheGuildsEntersBattlefieldEffect extends OneShotEffect<TabletOfTheGuildsEntersBattlefieldEffect> {

    public TabletOfTheGuildsEntersBattlefieldEffect() {
        super(Constants.Outcome.BoostCreature);
        staticText = "choose two colors";
    }

    public TabletOfTheGuildsEntersBattlefieldEffect(final TabletOfTheGuildsEntersBattlefieldEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (player != null && permanent != null) {
            String colors;
            ChoiceColor colorChoice = new ChoiceColor();
            colorChoice.setMessage("Choose the first color");
            while (!player.choose(Outcome.GainLife, colorChoice, game)) {
                game.debugMessage("player canceled choosing type. retrying.");
            }
            game.getState().setValue(permanent.getId() + "_color1", colorChoice.getColor().toString());
            colors = colorChoice.getChoice().toLowerCase() + " and ";

            colorChoice.getChoices().remove(colorChoice.getChoice());
            colorChoice.setMessage("Choose the second color");
            while (!player.choose(Outcome.GainLife, colorChoice, game)) {
                game.debugMessage("player canceled choosing type. retrying.");
            }
            game.getState().setValue(permanent.getId() + "_color2", colorChoice.getColor().toString());
            colors = colors + colorChoice.getChoice().toLowerCase();
            game.informPlayers(permanent.getName() + ": " + player.getName() + " has chosen " + colors);
        }
        return false;
    }

    @Override
    public TabletOfTheGuildsEntersBattlefieldEffect copy() {
        return new TabletOfTheGuildsEntersBattlefieldEffect(this);
    }

}

class TabletOfTheGuildsGainLifeEffect extends OneShotEffect<TabletOfTheGuildsGainLifeEffect> {

    public TabletOfTheGuildsGainLifeEffect() {
        super(Constants.Outcome.Neutral);
        staticText = "if it's at least one of the chosen colors, you gain 1 life for each of the chosen colors it is";
    }

    public TabletOfTheGuildsGainLifeEffect(final TabletOfTheGuildsGainLifeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        if (you != null) {
            Spell spell = game.getStack().getSpell(this.getTargetPointer().getFirst(game, source));
            if (spell != null) {
                ObjectColor color1 = new ObjectColor((String) game.getState().getValue(source.getSourceId() + "_color1"));
                ObjectColor color2 = new ObjectColor((String) game.getState().getValue(source.getSourceId() + "_color2"));
                int amount = 0;
                if (spell.getColor().contains(color1)) {
                    ++amount;
                }
                if (spell.getColor().contains(color2)) {
                    ++amount;
                }
                if (amount > 0) {
                    you.gainLife(amount, game);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public TabletOfTheGuildsGainLifeEffect copy() {
        return new TabletOfTheGuildsGainLifeEffect(this);
    }
}