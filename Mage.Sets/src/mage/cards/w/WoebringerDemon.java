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
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class WoebringerDemon extends CardImpl {

    public WoebringerDemon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");

        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of each player's upkeep, that player sacrifices a creature. If the player can't, sacrifice Woebringer Demon.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new WoebringerDemonEffect(), TargetController.ANY, false, true));
    }

    public WoebringerDemon(final WoebringerDemon card) {
        super(card);
    }

    @Override
    public WoebringerDemon copy() {
        return new WoebringerDemon(this);
    }
}

class WoebringerDemonEffect extends OneShotEffect {

    public WoebringerDemonEffect() {
        super(Outcome.Detriment);
        this.staticText = "that player sacrifices a creature. If the player can't, sacrifice {this}";
    }

    public WoebringerDemonEffect(final WoebringerDemonEffect effect) {
        super(effect);
    }

    @Override
    public WoebringerDemonEffect copy() {
        return new WoebringerDemonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Player currentPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
            if (currentPlayer != null) {
                TargetControlledCreaturePermanent target = new TargetControlledCreaturePermanent();
                target.setNotTarget(true);
                if (target.canChoose(currentPlayer.getId(), game)) {
                    currentPlayer.chooseTarget(Outcome.Sacrifice, target, source, game);
                    Permanent permanent = game.getPermanent(target.getFirstTarget());
                    if (permanent != null) {
                        permanent.sacrifice(source.getSourceId(), game);
                        return true;
                    }
                }
            }
            Permanent sourceObject = game.getPermanent(source.getSourceId());
            if (sourceObject != null && sourceObject.getZoneChangeCounter(game) == source.getSourceObjectZoneChangeCounter()) {
                sourceObject.sacrifice(source.getSourceId(), game);
            }
            return true;
        }
        return false;
    }
}
