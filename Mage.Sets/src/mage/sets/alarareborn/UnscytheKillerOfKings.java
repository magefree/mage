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
package mage.sets.alarareborn;

import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.ZombieToken;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public class UnscytheKillerOfKings extends CardImpl {

    public UnscytheKillerOfKings(UUID ownerId) {
        super(ownerId, 114, "Unscythe, Killer of Kings", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{U}{B}{B}{R}");
        this.expansionSetCode = "ARB";
        this.supertype.add("Legendary");
        this.subtype.add("Equipment");

        // Equipped creature gets +3/+3 and has first strike.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(3, 3)));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(FirstStrikeAbility.getInstance(), AttachmentType.EQUIPMENT)));

        // Whenever a creature dealt damage by equipped creature this turn dies, you may exile that card. If you do, put a 2/2 black Zombie creature token onto the battlefield.
        this.addAbility(new UnscytheKillerOfKingsTriggeredAbility(new UnscytheEffect()));

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(2), new TargetControlledCreaturePermanent()));
    }

    public UnscytheKillerOfKings(final UnscytheKillerOfKings card) {
        super(card);
    }

    @Override
    public UnscytheKillerOfKings copy() {
        return new UnscytheKillerOfKings(this);
    }
}

class UnscytheKillerOfKingsTriggeredAbility extends TriggeredAbilityImpl {

    public UnscytheKillerOfKingsTriggeredAbility(Effect effect) {
        super(Zone.ALL, effect, true);
    }

    public UnscytheKillerOfKingsTriggeredAbility(final UnscytheKillerOfKingsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public UnscytheKillerOfKingsTriggeredAbility copy() {
        return new UnscytheKillerOfKingsTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE && ((ZoneChangeEvent) event).isDiesEvent()) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.getTarget().getCardType().contains(CardType.CREATURE)) { // target token can't create Zombie
                Permanent equipment = game.getPermanent(getSourceId());
                // the currently equiped creature must have done damage to the dying creature
                if (equipment != null && equipment.getAttachedTo() != null) {
                    boolean damageDealt = false;
                    for (MageObjectReference mor : zEvent.getTarget().getDealtDamageByThisTurn()) {
                        if (mor.refersTo(equipment.getAttachedTo(), game)) {
                            damageDealt = true;
                            break;
                        }
                    }
                    if (damageDealt) {
                        Effect effect = this.getEffects().get(0);
                        effect.setTargetPointer(new FixedTarget(event.getTargetId()));
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature dealt damage by equipped creature this turn dies, " + super.getRule();
    }
}

class UnscytheEffect extends OneShotEffect {

    public UnscytheEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "you may exile that card. If you do, put a 2/2 black Zombie creature token onto the battlefield";
    }

    public UnscytheEffect(final UnscytheEffect effect) {
        super(effect);
    }

    @Override
    public UnscytheEffect copy() {
        return new UnscytheEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = game.getCard(targetPointer.getFirst(game, source));
            if (card != null && game.getState().getZone(card.getId()).equals(Zone.GRAVEYARD) && controller.moveCardToExileWithInfo(card, null, "", source.getSourceId(), game, Zone.GRAVEYARD, true)) {
                ZombieToken zombie = new ZombieToken("ALA");
                return zombie.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId());
            }
            return true;
        }
        return false;
    }
}
