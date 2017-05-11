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
package mage.cards.m;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author spjspj
 */
public class MartyrsBond extends CardImpl {

    public MartyrsBond(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{W}{W}");

        // Whenever Martyr's Bond or another nonland permanent you control is put into a graveyard from the battlefield, each opponent sacrifices a permanent that shares a card type with it.
        this.addAbility(new MartyrsBondTriggeredAbility());
    }

    public MartyrsBond(final MartyrsBond card) {
        super(card);
    }

    @Override
    public MartyrsBond copy() {
        return new MartyrsBond(this);
    }
}

class MartyrsBondTriggeredAbility extends TriggeredAbilityImpl {

    public MartyrsBondTriggeredAbility() {
        super(Zone.BATTLEFIELD, new MartyrsBondEffect());
    }

    public MartyrsBondTriggeredAbility(final MartyrsBondTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MartyrsBondTriggeredAbility copy() {
        return new MartyrsBondTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (((ZoneChangeEvent) event).getToZone() == Zone.GRAVEYARD
                && ((ZoneChangeEvent) event).getFromZone() == Zone.BATTLEFIELD) {
            Permanent permanent = (Permanent) game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD);
            if (permanent.getControllerId().equals(this.getControllerId()) && !permanent.isLand()) {
                for (Effect effect : this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(permanent.getId()));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} or another nonland permanent you control is put into a graveyard from the battlefield, each opponent sacrifices a permanent that shares a card type with it.";
    }

}

class MartyrsBondEffect extends OneShotEffect {

    public MartyrsBondEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "each opponent sacrifices a permanent that shares a card type with it";
    }

    public MartyrsBondEffect(final MartyrsBondEffect effect) {
        super(effect);
    }

    @Override
    public MartyrsBondEffect copy() {
        return new MartyrsBondEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<UUID> perms = new ArrayList<>();
        if (source != null) {
            Permanent saccedPermanent = game.getPermanentOrLKIBattlefield(getTargetPointer().getFirst(game, source));
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null && saccedPermanent != null) {
                FilterControlledPermanent filter = new FilterControlledPermanent();
                String message = "permanent with type (";
                boolean firstType = true;

                ArrayList<CardTypePredicate> cardTypes = new ArrayList<>();

                for (CardType type : saccedPermanent.getCardType()) {
                    cardTypes.add(new CardTypePredicate(type));
                    if (firstType) {
                        message += type;
                        firstType = false;
                    } else {
                        message += " or " + type;
                    }
                }
                message += ") to sacrifice";
                filter.add(Predicates.or(cardTypes));
                filter.setMessage(message);

                for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                    Player player = game.getPlayer(playerId);
                    if (player != null && !playerId.equals(controller.getId())) {
                        TargetControlledPermanent target = new TargetControlledPermanent(1, 1, filter, true);
                        if (target.canChoose(playerId, game)) {
                            player.chooseTarget(Outcome.Sacrifice, target, source, game);
                            perms.add(target.getFirstTarget());
                        }
                    }
                }

                boolean saccedPermaents = false;
                for (UUID permID : perms) {
                    Permanent permanent = game.getPermanent(permID);
                    if (permanent != null) {
                        permanent.sacrifice(source.getSourceId(), game);
                        saccedPermaents = true;
                    }
                }

                return saccedPermaents;
            }
        }
        return false;
    }
}
