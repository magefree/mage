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
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.ElementalShamanToken;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Styxo
 */
public class RebellionOfTheFlamekin extends CardImpl {

    public RebellionOfTheFlamekin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.TRIBAL,CardType.ENCHANTMENT},"{3}{R}");
        this.subtype.add("Elemental");

        // Whenever you clash, you may pay {1}. If you do create a 3/1 Red Elemental Shaman creature token in play. If you won that token gains haste
        this.addAbility(new RebellionOfTheFlamekinTriggeredAbility());
    }

    public RebellionOfTheFlamekin(final RebellionOfTheFlamekin card) {
        super(card);
    }

    @Override
    public RebellionOfTheFlamekin copy() {
        return new RebellionOfTheFlamekin(this);
    }
}

class RebellionOfTheFlamekinTriggeredAbility extends TriggeredAbilityImpl {

    public RebellionOfTheFlamekinTriggeredAbility() {
        super(Zone.BATTLEFIELD, new RebellionOfTheFlamekinEffect());
    }

    public RebellionOfTheFlamekinTriggeredAbility(final RebellionOfTheFlamekinTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RebellionOfTheFlamekinTriggeredAbility copy() {
        return new RebellionOfTheFlamekinTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CLASHED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        boolean youWonTheClash = false;
         if (event.getData().equals("controller") && event.getPlayerId().equals(getControllerId())
                    || event.getData().equals("opponent") && event.getTargetId().equals(getControllerId())) {
            youWonTheClash = true;
        }
        for (Effect effect : getEffects()) {
            if (effect instanceof RebellionOfTheFlamekinEffect) {
                effect.setValue("clash", youWonTheClash);
            }
        }
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever you clash, you may pay {1}. If you do create a 3/1 Red Elemental Shaman creature token in play. If you won that token gains haste until end of turn";
    }
}

class RebellionOfTheFlamekinEffect extends OneShotEffect {

    public RebellionOfTheFlamekinEffect() {
        super(Outcome.PutCreatureInPlay);
    }

    public RebellionOfTheFlamekinEffect(final RebellionOfTheFlamekinEffect effect) {
        super(effect);
    }

    @Override
    public RebellionOfTheFlamekinEffect copy() {
        return new RebellionOfTheFlamekinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            CreateTokenEffect createTokenEffect = new CreateTokenEffect(new ElementalShamanToken("LRW"));
            DoIfCostPaid doIfCostPaid = new DoIfCostPaid(createTokenEffect, new ManaCostsImpl("{1}"));
            doIfCostPaid.apply(game, source);
            Permanent token = game.getPermanent(createTokenEffect.getLastAddedTokenId());
            if (token != null && (boolean) (this.getValue("clash"))) {
                ContinuousEffect continuousEffect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn);
                continuousEffect.setTargetPointer(new FixedTarget(createTokenEffect.getLastAddedTokenId()));
                game.addEffect(continuousEffect, source);
            }
            return true;

        }
        return false;

    }
}
