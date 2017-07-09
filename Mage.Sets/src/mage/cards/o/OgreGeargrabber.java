/*
 *  Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
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
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class OgreGeargrabber extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Equipment an opponent controls");

    static {
        filter.add(new CardTypePredicate(CardType.ARTIFACT));
        filter.add(new SubtypePredicate(SubType.EQUIPMENT));
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public OgreGeargrabber(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}{R}");
        this.subtype.add("Ogre");
        this.subtype.add("Warrior");

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        Ability ability = new AttacksTriggeredAbility(new OgreGeargrabberEffect1(), false);
        ability.addEffect(new GainControlTargetEffect(Duration.EndOfTurn));
        ability.addTarget(new TargetPermanent(1, 1, filter, false));
        this.addAbility(ability);
    }

    public OgreGeargrabber(final OgreGeargrabber card) {
        super(card);
    }

    @Override
    public OgreGeargrabber copy() {
        return new OgreGeargrabber(this);
    }

}

class OgreGeargrabberEffect1 extends OneShotEffect {

    public OgreGeargrabberEffect1() {
        super(Outcome.GainControl);
        staticText = "Attach it to {this}. When you lose control of that Equipment, unattach it.";
    }

    public OgreGeargrabberEffect1(final OgreGeargrabberEffect1 effect) {
        super(effect);
    }

    @Override
    public OgreGeargrabberEffect1 copy() {
        return new OgreGeargrabberEffect1(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID equipmentId = source.getFirstTarget();
        if (equipmentId != null) {
            OgreGeargrabberDelayedTriggeredAbility delayedAbility = new OgreGeargrabberDelayedTriggeredAbility(equipmentId);
            game.addDelayedTriggeredAbility(delayedAbility, source);
            Permanent equipment = game.getPermanent(equipmentId);
            if (equipment != null) {
                Permanent ogre = game.getPermanent(source.getSourceId());
                if (ogre != null) {
                    ogre.addAttachment(equipmentId, game);
                }
            }
            return true;
        }
        return false;
    }

}

class OgreGeargrabberDelayedTriggeredAbility extends DelayedTriggeredAbility {

    private UUID equipmentId;

    OgreGeargrabberDelayedTriggeredAbility(UUID equipmentId) {
        super(new OgreGeargrabberEffect2(equipmentId));
        this.equipmentId = equipmentId;
    }

    OgreGeargrabberDelayedTriggeredAbility(OgreGeargrabberDelayedTriggeredAbility ability) {
        super(ability);
        this.equipmentId = ability.equipmentId;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.LOST_CONTROL;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(controllerId) && event.getTargetId().equals(equipmentId);
    }

    @Override
    public OgreGeargrabberDelayedTriggeredAbility copy() {
        return new OgreGeargrabberDelayedTriggeredAbility(this);
    }
}

class OgreGeargrabberEffect2 extends OneShotEffect {

    private UUID equipmentId;

    public OgreGeargrabberEffect2(UUID equipmentId) {
        super(Outcome.Neutral);
        this.equipmentId = equipmentId;
        staticText = "When you lose control of that Equipment, unattach it.";
    }

    public OgreGeargrabberEffect2(final OgreGeargrabberEffect2 effect) {
        super(effect);
        this.equipmentId = effect.equipmentId;
    }

    @Override
    public OgreGeargrabberEffect2 copy() {
        return new OgreGeargrabberEffect2(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (equipmentId != null) {
            Permanent equipment = game.getPermanent(equipmentId);
            if (equipment != null && equipment.getAttachedTo() != null) {
                Permanent attachedTo = game.getPermanent(equipment.getAttachedTo());
                if (attachedTo != null) {
                    attachedTo.removeAttachment(equipmentId, game);
                }
            }
            return true;
        }
        return false;
    }

}
