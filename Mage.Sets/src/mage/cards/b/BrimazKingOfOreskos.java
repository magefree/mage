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
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.BlocksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.game.permanent.token.CatSoldierCreatureToken;

/**
 *
 * @author LevelX2
 */
public class BrimazKingOfOreskos extends CardImpl {

    public BrimazKingOfOreskos(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");
        this.supertype.add("Legendary");
        this.subtype.add("Cat");
        this.subtype.add("Soldier");

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever Brimaz, King of Oreskos attacks, create a 1/1 white Cat Soldier creature token with vigilance attacking.
        this.addAbility(new AttacksTriggeredAbility(new CreateTokenEffect(new CatSoldierCreatureToken(), 1, false, true), false));

        // Whenever Brimaz blocks a creature, create a 1/1 white Cat Soldier creature token with vigilance blocking that creature.
        this.addAbility(new BlocksTriggeredAbility(new BrimazKingOfOreskosEffect(), false, true));
    }

    public BrimazKingOfOreskos(final BrimazKingOfOreskos card) {
        super(card);
    }

    @Override
    public BrimazKingOfOreskos copy() {
        return new BrimazKingOfOreskos(this);
    }
}

class BrimazKingOfOreskosEffect extends OneShotEffect {

    public BrimazKingOfOreskosEffect() {
        super(Outcome.Benefit);
        this.staticText = "create a 1/1 white Cat Soldier creature token with vigilance blocking that creature";
    }

    public BrimazKingOfOreskosEffect(final BrimazKingOfOreskosEffect effect) {
        super(effect);
    }

    @Override
    public BrimazKingOfOreskosEffect copy() {
        return new BrimazKingOfOreskosEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());

        if (controller != null) {
            Token token = new CatSoldierCreatureToken();
            token.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId());
            Permanent attackingCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (attackingCreature != null && game.getState().getCombat() != null) {
                // Possible ruling (see Aetherplasm)
                // The token you created is blocking the attacking creature,
                // even if the block couldn't legally be declared (for example, if that creature
                // enters the battlefield tapped, or it can't block, or the attacking creature
                // has protection from it)
                CombatGroup combatGroup = game.getState().getCombat().findGroup(attackingCreature.getId());
                if (combatGroup != null) {
                    for (UUID tokenId : token.getLastAddedTokenIds()) {
                        Permanent catToken = game.getPermanent(tokenId);
                        if (catToken != null) {
                            combatGroup.addBlocker(tokenId, source.getControllerId(), game);
                            game.getCombat().addBlockingGroup(tokenId, attackingCreature.getId(), controller.getId(), game);
                        }
                    }
                    combatGroup.pickBlockerOrder(attackingCreature.getControllerId(), game);
                }
            }
            return true;
        }
        return false;
    }
}
