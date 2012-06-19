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
package mage.sets.innistrad;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author BetaSteward
 */
public class HereticsPunishment extends CardImpl<HereticsPunishment> {

    public HereticsPunishment(UUID ownerId) {
        super(ownerId, 147, "Heretic's Punishment", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{4}{R}");
        this.expansionSetCode = "ISD";

        this.color.setRed(true);

        // {3}{R}: Choose target creature or player, then put the top three cards of your library into your graveyard. Heretic's Punishment deals damage to that creature or player equal to the highest converted mana cost among those cards.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new HereticsPunishmentEffect(), new ManaCostsImpl("{3}{R}"));
        ability.addTarget(new TargetCreatureOrPlayer());
        this.addAbility(ability);
    }

    public HereticsPunishment(final HereticsPunishment card) {
        super(card);
    }

    @Override
    public HereticsPunishment copy() {
        return new HereticsPunishment(this);
    }
}

class HereticsPunishmentEffect extends OneShotEffect<HereticsPunishmentEffect> {

    public HereticsPunishmentEffect() {
        super(Outcome.Damage);
        staticText = "Choose target creature or player, then put the top three cards of your library into your graveyard. Heretic's Punishment deals damage to that creature or player equal to the highest converted mana cost among those cards";
    }

    public HereticsPunishmentEffect(final HereticsPunishmentEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            int maxCost = 0;
            for (int i = 0; i < Math.min(3, player.getLibrary().size()); i++) {
                Card card = player.getLibrary().removeFromTop(game);
                if (card != null) { 
                    card.moveToZone(Zone.GRAVEYARD, source.getSourceId(), game, true);
                    int test = card.getManaCost().convertedManaCost();
                    if (test > maxCost)
                        maxCost = test;
                }
            }
            Permanent permanent = game.getPermanent(targetPointer.getFirst(game, source));
            if (permanent != null) {
                permanent.damage(maxCost, source.getSourceId(), game, true, false);
                return true;
            }
            Player targetPlayer = game.getPlayer(targetPointer.getFirst(game, source));
            if (targetPlayer != null) {
                targetPlayer.damage(maxCost, source.getSourceId(), game, false, true);
                return true;
            }
        }
        return false;
    }

    @Override
    public HereticsPunishmentEffect copy() {
        return new HereticsPunishmentEffect(this);
    }

}