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
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author spjspj
 */
public class SnickeringSquirrel extends CardImpl {

    public SnickeringSquirrel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add("Squirrel");
        this.subtype.add("Advisor");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // You may tap Snickering Squirrel to increase the result of a die any player rolled by 1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SnickeringSquirrelEffect()));
    }

    public SnickeringSquirrel(final SnickeringSquirrel card) {
        super(card);
    }

    @Override
    public SnickeringSquirrel copy() {
        return new SnickeringSquirrel(this);
    }
}

class SnickeringSquirrelEffect extends ReplacementEffectImpl {

    SnickeringSquirrelEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "You may tap {this} to increase the result of a die any player rolled by 1";
    }

    SnickeringSquirrelEffect(final SnickeringSquirrelEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());

        if (controller != null) {
            Permanent permanent = game.getPermanent(source.getSourceId());
            if (permanent != null && permanent.canTap() && !permanent.isTapped()) {
                if (controller.chooseUse(Outcome.AIDontUseIt, "Do you want to tap this to increase the result of a die any player rolled by 1?", null, "Yes", "No", source, game)) {
                    permanent.tap(game);
                    // ignore planar dies (dice roll amount of planar dies is equal to 0)
                    if (event.getAmount() > 0) {
                        event.setAmount(event.getAmount() + 1);
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ROLL_DICE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public SnickeringSquirrelEffect copy() {
        return new SnickeringSquirrelEffect(this);
    }
}
