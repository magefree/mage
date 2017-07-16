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
package mage.cards.n;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.continuous.SetCardColorAttachedEffect;
import mage.abilities.effects.common.continuous.SetCardSubtypeAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.IntimidateAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 *
 * @author nantuko
 */
public class NimDeathmantle extends CardImpl {

    public NimDeathmantle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");
        this.subtype.add("Equipment");

        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(4)));

        // Equipped creature gets +2/+2, has intimidate, and is a black Zombie.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(2, 2)));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(IntimidateAbility.getInstance(), AttachmentType.EQUIPMENT)));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SetCardColorAttachedEffect(ObjectColor.BLACK, Duration.WhileOnBattlefield, AttachmentType.EQUIPMENT)));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SetCardSubtypeAttachedEffect(SubType.ZOMBIE, Duration.WhileOnBattlefield, AttachmentType.EQUIPMENT)));

        // Whenever a nontoken creature is put into your graveyard from the battlefield, you may pay {4}. If you do, return that card to the battlefield and attach Nim Deathmantle to it.
        this.addAbility(new NimDeathmantleTriggeredAbility());
    }

    public NimDeathmantle(final NimDeathmantle card) {
        super(card);
    }

    @Override
    public NimDeathmantle copy() {
        return new NimDeathmantle(this);
    }
}

class NimDeathmantleTriggeredAbility extends TriggeredAbilityImpl {

    NimDeathmantleTriggeredAbility() {
        super(Zone.BATTLEFIELD, new NimDeathmantleEffect(), true);
    }

    NimDeathmantleTriggeredAbility(NimDeathmantleTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public NimDeathmantleTriggeredAbility copy() {
        return new NimDeathmantleTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        Permanent permanent = zEvent.getTarget();
        if (permanent != null
                && permanent.getOwnerId().equals(this.controllerId)
                && zEvent.getToZone() == Zone.GRAVEYARD
                && zEvent.getFromZone() == Zone.BATTLEFIELD
                && !(permanent instanceof PermanentToken)
                && permanent.isCreature()) {

            getEffects().get(0).setTargetPointer(new FixedTarget(permanent.getId()));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a nontoken creature is put into your graveyard from the battlefield, you may pay {4}. If you do, return that card to the battlefield and attach Nim Deathmantle to it.";
    }
}

class NimDeathmantleEffect extends OneShotEffect {

    private final Cost cost = new GenericManaCost(4);

    public NimDeathmantleEffect() {
        super(Outcome.Benefit);

    }

    public NimDeathmantleEffect(NimDeathmantleEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent equipment = game.getPermanent(source.getSourceId());
        if (controller != null && equipment != null) {
            if (controller.chooseUse(Outcome.Benefit, equipment.getName() + " - Pay " + cost.getText() + '?', source, game)) {
                cost.clearPaid();
                if (cost.pay(source, game, source.getSourceId(), source.getControllerId(), false, null)) {
                    UUID target = targetPointer.getFirst(game, source);
                    if (target != null) {
                        Card card = game.getCard(target);
                        // check if it's still in graveyard
                        if (card != null && game.getState().getZone(card.getId()) == Zone.GRAVEYARD) {
                            if (controller.moveCards(card, Zone.BATTLEFIELD, source, game)) {
                                Permanent permanent = game.getPermanent(card.getId());
                                if (permanent != null) {
                                    permanent.addAttachment(equipment.getId(), game);
                                }
                            }
                        }
                    }
                }
            }
            return true;
        }

        return false;
    }

    @Override
    public NimDeathmantleEffect copy() {
        return new NimDeathmantleEffect(this);
    }

}
