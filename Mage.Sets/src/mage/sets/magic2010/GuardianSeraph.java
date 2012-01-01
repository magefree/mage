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
package mage.sets.magic2010;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;

/**
 *
 * @author North
 */
public class GuardianSeraph extends CardImpl<GuardianSeraph> {

    public GuardianSeraph(UUID ownerId) {
        super(ownerId, 13, "Guardian Seraph", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");
        this.expansionSetCode = "M10";
        this.subtype.add("Angel");

        this.color.setWhite(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        this.addAbility(FlyingAbility.getInstance());
        // If a source an opponent controls would deal damage to you, prevent 1 of that damage.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GuardianSeraphEffect()));
    }

    public GuardianSeraph(final GuardianSeraph card) {
        super(card);
    }

    @Override
    public GuardianSeraph copy() {
        return new GuardianSeraph(this);
    }
}

class GuardianSeraphEffect extends PreventionEffectImpl<GuardianSeraphEffect> {

    public GuardianSeraphEffect() {
        super(Duration.WhileOnBattlefield);
        this.staticText = "If a source an opponent controls would deal damage to you, prevent 1 of that damage";
    }

    public GuardianSeraphEffect(GuardianSeraphEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        GameEvent preventEvent = new GameEvent(GameEvent.EventType.PREVENT_DAMAGE, source.getFirstTarget(), source.getId(), source.getControllerId(), 1, false);
        if (!game.replaceEvent(preventEvent)) {
            int damage = event.getAmount();
            if (damage > 0) {
                event.setAmount(damage - 1);
                game.informPlayers("1 damage has been prevented.");
            }
            game.fireEvent(GameEvent.getEvent(GameEvent.EventType.PREVENTED_DAMAGE, source.getFirstTarget(), source.getId(), source.getControllerId(), 1));
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType().equals(GameEvent.EventType.DAMAGE_PLAYER)) {
            UUID sourceControllerId = null;
            Permanent permanent = game.getPermanent(event.getSourceId());
            if (permanent != null) {
                sourceControllerId = permanent.getControllerId();
            } else {
                StackObject sourceStackObject = game.getStack().getStackObject(event.getSourceId());
                if (sourceStackObject != null) {
                    sourceControllerId = sourceStackObject.getControllerId();
                }
            }
            if (event.getTargetId().equals(source.getControllerId()) && game.getOpponents(source.getControllerId()).contains(sourceControllerId)) {
                return super.applies(event, source, game);
            }
        }
        return false;
    }

    @Override
    public GuardianSeraphEffect copy() {
        return new GuardianSeraphEffect(this);
    }
}
