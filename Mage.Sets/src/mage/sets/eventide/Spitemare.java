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
package mage.sets.eventide;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author jeffwadsworth
 */
public class Spitemare extends CardImpl<Spitemare> {

    public Spitemare(UUID ownerId) {
        super(ownerId, 147, "Spitemare", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{R/W}{R/W}");
        this.expansionSetCode = "EVE";
        this.subtype.add("Elemental");

        this.color.setRed(true);
        this.color.setWhite(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Spitemare is dealt damage, it deals that much damage to target creature or player.
        Ability ability = new SpitemareTriggeredAbility();
        ability.addTarget(new TargetCreatureOrPlayer(true));
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

class SpitemareTriggeredAbility extends TriggeredAbilityImpl<SpitemareTriggeredAbility> {

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
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGED_CREATURE && event.getTargetId().equals(this.sourceId)) {
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

class SpitemareEffect extends OneShotEffect<SpitemareEffect> {

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
        Player target = game.getPlayer(source.getFirstTarget());
        if (target != null) {
            target.damage((Integer) this.getValue("damageAmount"), source.getSourceId(), game, false, true);
        }
        return true;
    }
}