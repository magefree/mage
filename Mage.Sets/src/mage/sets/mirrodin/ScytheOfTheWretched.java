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
package mage.sets.mirrodin;

import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.target.targetpointer.TargetPointer;

/**
 *
 * @author Jason E. Wall
 *
 */
public class ScytheOfTheWretched extends CardImpl {

    public ScytheOfTheWretched(UUID ownerId) {
        super(ownerId, 239, "Scythe of the Wretched", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{2}");
        this.expansionSetCode = "MRD";
        this.subtype.add("Equipment");

        // Equipped creature gets +2/+2.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(2, 2, Duration.WhileOnBattlefield)));

        // Whenever a creature dealt damage by equipped creature this turn dies, return that card to the battlefield under your control. Attach Scythe of the Wretched to that creature.
        this.addAbility(new ScytheOfTheWretchedTriggeredAbility());

        // Equip {4}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(4)));
    }

    public ScytheOfTheWretched(final ScytheOfTheWretched card) {
        super(card);
    }

    @Override
    public ScytheOfTheWretched copy() {
        return new ScytheOfTheWretched(this);
    }
}

class ScytheOfTheWretchedTriggeredAbility extends TriggeredAbilityImpl {

    public ScytheOfTheWretchedTriggeredAbility() {
        super(Zone.ALL, new ScytheOfTheWretchedReanimateEffect(), false);
    }

    public ScytheOfTheWretchedTriggeredAbility(final ScytheOfTheWretchedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ScytheOfTheWretchedTriggeredAbility copy() {
        return new ScytheOfTheWretchedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zoneChange = (ZoneChangeEvent) event;
        if (zoneChange.isDiesEvent() && zoneChange.getTarget().getCardType().contains(CardType.CREATURE)) {
            Permanent equippedCreature = getEquippedCreature(game);
            for (MageObjectReference mor : zoneChange.getTarget().getDealtDamageByThisTurn()) {
                Permanent permanent = (Permanent) game.getLastKnownInformation(mor.getSourceId(), Zone.BATTLEFIELD);
                if ((equippedCreature != null && mor.refersTo(equippedCreature, game))
                        || (permanent != null && permanent.getAttachments().contains(getSourceId()))) {
                    setTarget(new FixedTarget(event.getTargetId()));
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature dealt damage by equipped creature this turn dies, " + super.getRule();
    }

    private void setTarget(TargetPointer target) {
        for (Effect effect : getEffects()) {
            effect.setTargetPointer(target);
        }
    }

    private Permanent getEquippedCreature(Game game) {
        Permanent equipment = game.getPermanent(getSourceId());
        if (equipment != null && equipment.getAttachedTo() != null) {
            return game.getPermanent(equipment.getAttachedTo());
        }
        return null;
    }
}

class ScytheOfTheWretchedReanimateEffect extends OneShotEffect {

    public ScytheOfTheWretchedReanimateEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "return that card to the battlefield under your control. Attach {this} to that creature";
    }

    public ScytheOfTheWretchedReanimateEffect(final ScytheOfTheWretchedReanimateEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (card != null && controller != null) {
            Zone currentZone = game.getState().getZone(card.getId());
            controller.putOntoBattlefieldWithInfo(card, game, currentZone, source.getSourceId());
            Effect effect = new AttachEffect(Outcome.AddAbility);
            effect.setTargetPointer(new FixedTarget(card.getId()));
            effect.apply(game, source);
            return true;
        }

        return false;
    }

    @Override
    public Effect copy() {
        return new ScytheOfTheWretchedReanimateEffect(this);
    }
}
