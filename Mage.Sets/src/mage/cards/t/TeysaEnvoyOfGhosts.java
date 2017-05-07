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
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.Zone;
import static mage.filter.StaticFilters.FILTER_PERMANENT_CREATURES;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TeysaEnvoyOfGhostsToken;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class TeysaEnvoyOfGhosts extends CardImpl {

    public TeysaEnvoyOfGhosts(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}{B}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add("Human");
        this.subtype.add("Advisor");

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
        // protection from creatures
        this.addAbility(new ProtectionAbility(FILTER_PERMANENT_CREATURES));
        // Whenever a creature deals combat damage to you, destroy that creature. Create a 1/1 white and black Spirit creature token with flying.
        this.addAbility(new TeysaEnvoyOfGhostsTriggeredAbility());

    }

    public TeysaEnvoyOfGhosts(final TeysaEnvoyOfGhosts card) {
        super(card);
    }

    @Override
    public TeysaEnvoyOfGhosts copy() {
        return new TeysaEnvoyOfGhosts(this);
    }
}

class TeysaEnvoyOfGhostsTriggeredAbility extends TriggeredAbilityImpl {

    public TeysaEnvoyOfGhostsTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DestroyTargetEffect());
        this.addEffect(new CreateTokenEffect(new TeysaEnvoyOfGhostsToken(), 1));

    }

    public TeysaEnvoyOfGhostsTriggeredAbility(final TeysaEnvoyOfGhostsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TeysaEnvoyOfGhostsTriggeredAbility copy() {
        return new TeysaEnvoyOfGhostsTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedPlayerEvent damageEvent = (DamagedPlayerEvent) event;
        Permanent sourcePermanent = game.getPermanent(event.getSourceId());
        if (damageEvent.getPlayerId().equals(getControllerId())
                && damageEvent.isCombatDamage()
                && sourcePermanent != null
                && sourcePermanent.isCreature()) {
            game.getState().setValue(sourceId.toString(), sourcePermanent.getControllerId());
            getEffects().get(0).setTargetPointer(new FixedTarget(event.getSourceId()));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature deals combat damage to you, destroy that creature. Create a 1/1 white and black Spirit creature token with flying.";
    }

}
