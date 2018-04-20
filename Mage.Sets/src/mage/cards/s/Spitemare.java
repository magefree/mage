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
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author jeffwadsworth
 */
public class Spitemare extends CardImpl {

    public Spitemare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R/W}{R/W}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Spitemare is dealt damage, it deals that much damage to target creature or player.
        Ability ability = new SpitemareTriggeredAbility();
        ability.addTarget(new TargetCreatureOrPlayer());
        this.addAbility(ability);

    }

    public Spitemare(final Spitemare card) {
        super(card);
    }

    @Override
    public Spitemare copy() {
        return new Spitemare(this);
    }
}

class SpitemareTriggeredAbility extends TriggeredAbilityImpl {

    public SpitemareTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SpitemareEffect());
    }

    public SpitemareTriggeredAbility(final SpitemareTriggeredAbility effect) {
        super(effect);
    }

    @Override
    public SpitemareTriggeredAbility copy() {
        return new SpitemareTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DAMAGED_CREATURE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(this.sourceId)) {
            this.getEffects().get(0).setValue("damageAmount", event.getAmount());
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} is dealt damage, " + super.getRule();
    }
}

class SpitemareEffect extends OneShotEffect {

    public SpitemareEffect() {
        super(Outcome.Damage);
        staticText = "it deals that much damage to target creature or player";
    }

    public SpitemareEffect(final SpitemareEffect effect) {
        super(effect);
    }

    @Override
    public SpitemareEffect copy() {
        return new SpitemareEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player != null) {
            player.damage((Integer) this.getValue("damageAmount"), source.getSourceId(), game, false, true);
            return true;
        }
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            permanent.damage((Integer) this.getValue("damageAmount"), source.getSourceId(), game, false, true);
            return true;
        }
        return false;
    }
}
