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
package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author jeffwadsworth
 */
public class GreatbowDoyen extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Archer creatures you control");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(new SubtypePredicate(SubType.ARCHER));
    }

    public GreatbowDoyen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add("Elf");
        this.subtype.add("Archer");

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Other Archer creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, filter, true)));

        // Whenever an Archer you control deals damage to a creature, that Archer deals that much damage to that creature's controller.
        this.addAbility(new GreatbowDoyenTriggeredAbility());
    }

    public GreatbowDoyen(final GreatbowDoyen card) {
        super(card);
    }

    @Override
    public GreatbowDoyen copy() {
        return new GreatbowDoyen(this);
    }
}

class GreatbowDoyenTriggeredAbility extends TriggeredAbilityImpl {

    public GreatbowDoyenTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GreatbowDoyenEffect());
    }

    public GreatbowDoyenTriggeredAbility(final GreatbowDoyenTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GreatbowDoyenTriggeredAbility copy() {
        return new GreatbowDoyenTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DAMAGED_CREATURE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent creature = game.getPermanent(event.getSourceId());
        Permanent damagedCreature = game.getPermanent(event.getTargetId());
        if (creature != null && damagedCreature != null 
                && creature.isCreature()
                && creature.hasSubtype("Archer", game)
                && creature.getControllerId().equals(controllerId)) {
            this.getEffects().get(0).setValue("damageAmount", event.getAmount());
            this.getEffects().get(0).setValue("controller", damagedCreature.getControllerId());
            this.getEffects().get(0).setValue("source", event.getSourceId());
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an Archer you control deals damage to a creature, " + super.getRule();
    }
}

class GreatbowDoyenEffect extends OneShotEffect {

    public GreatbowDoyenEffect() {
        super(Outcome.Damage);
        this.staticText = "that Archer deals that much damage to that creature's controller";
    }

    public GreatbowDoyenEffect(final GreatbowDoyenEffect effect) {
        super(effect);
    }

    @Override
    public GreatbowDoyenEffect copy() {
        return new GreatbowDoyenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Integer damageAmount = (Integer) this.getValue("damageAmount");
        UUID controllerId = (UUID) this.getValue("controller");
        UUID sourceOfDamage = (UUID) this.getValue("source");
        if (damageAmount != null && controllerId != null) {
            Permanent permanent = game.getPermanent(sourceOfDamage);
            if (permanent == null) {
                permanent = (Permanent) game.getLastKnownInformation(sourceOfDamage, Zone.BATTLEFIELD);
            }
            if (permanent != null) {
                Player player = game.getPlayer(controllerId);
                if (player != null) {
                    player.damage(damageAmount, sourceOfDamage, game, false, true);
                    game.informPlayers(new StringBuilder(permanent.getName()).append(" deals ").append(damageAmount).append(" damage to ").append(player.getLogName()).toString());
                    return true;
                }
            }
        }
        return false;
    }
}
