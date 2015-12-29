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
package mage.sets.torment;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.costs.AdjustingSourceCosts;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeTargetEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class SoulScourge extends CardImpl {

    public SoulScourge(UUID ownerId) {
        super(ownerId, 85, "Soul Scourge", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{4}{B}");
        this.expansionSetCode = "TOR";
        this.subtype.add("Nightmare");
        this.subtype.add("Horror");

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Soul Scourge enters the battlefield, target player loses 3 life.
        Ability ability = new SoulScourgeEntersBattlefieldTriggeredAbility();
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
        // When Soul Scourge leaves the battlefield, that player gains 3 life.
        this.addAbility(new SoulScourgeLeavesBattlefieldTriggeredAbility());
    }

    public SoulScourge(final SoulScourge card) {
        super(card);
    }

    @Override
    public SoulScourge copy() {
        return new SoulScourge(this);
    }
}

class SoulScourgeEntersBattlefieldTriggeredAbility extends EntersBattlefieldTriggeredAbility implements AdjustingSourceCosts {

    public SoulScourgeEntersBattlefieldTriggeredAbility() {
        super(new LoseLifeTargetEffect(3), false);
    }

    public SoulScourgeEntersBattlefieldTriggeredAbility(SoulScourgeEntersBattlefieldTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SoulScourgeEntersBattlefieldTriggeredAbility copy() {
        return new SoulScourgeEntersBattlefieldTriggeredAbility(this);
    }

    @Override
    public void adjustCosts(Ability ability, Game game) {
        Player player = game.getPlayer(ability.getFirstTarget());
        if (player != null) {
            String key = CardUtil.getCardZoneString("targetPlayer", this.getSourceId(), game);
            game.getState().setValue(key, player.getId());
        }
    }
}

class SoulScourgeLeavesBattlefieldTriggeredAbility extends LeavesBattlefieldTriggeredAbility {

    public SoulScourgeLeavesBattlefieldTriggeredAbility() {
        super(new GainLifeTargetEffect(3), false);
    }

    public SoulScourgeLeavesBattlefieldTriggeredAbility(SoulScourgeLeavesBattlefieldTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (super.checkTrigger(event, game)) {
            String key = CardUtil.getCardZoneString("targetPlayer", this.getSourceId(), game, true);
            Object object = game.getState().getValue(key);
            if (object instanceof UUID) {
                for (Effect effect : this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget((UUID) object));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public SoulScourgeLeavesBattlefieldTriggeredAbility copy() {
        return new SoulScourgeLeavesBattlefieldTriggeredAbility(this);
    }
}
