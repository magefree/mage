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
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.DragonTokenGold;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Saga
 */
public class SwordOfDungeonsAndDragons extends CardImpl {
    
    private static final FilterCard filter = new FilterCard("Rogues and from Clerics");
     static {filter.add(Predicates.or(
            new SubtypePredicate(SubType.ROGUE),
            new SubtypePredicate(SubType.CLERIC)
            ));
    }

    public SwordOfDungeonsAndDragons(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+2 and has protection from Rogues and from Clerics.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(2, 2));
        Effect effect = new GainAbilityAttachedEffect(new ProtectionAbility(filter), AttachmentType.EQUIPMENT);
        effect.setText("and has protection from Rogues and from Clerics");
        ability.addEffect(effect);
        this.addAbility(ability);

        // Whenever equipped creature deals combat damage to a player, you create a 4/4 gold Dragon creature token with flying and roll a d20. If you roll a 20, repeat this process.
        this.addAbility(new SwordOfDungeonsAndDragonsAbility());

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(2)));
    }

    public SwordOfDungeonsAndDragons(final SwordOfDungeonsAndDragons card) {
        super(card);
    }

    @Override
    public SwordOfDungeonsAndDragons copy() {
        return new SwordOfDungeonsAndDragons(this);
    }
}

class SwordOfDungeonsAndDragonsAbility extends TriggeredAbilityImpl {

    public SwordOfDungeonsAndDragonsAbility() {
        super(Zone.BATTLEFIELD, new SwordOfDungeonsAndDragonsEffect(),false);
    }

    public SwordOfDungeonsAndDragonsAbility(final SwordOfDungeonsAndDragonsAbility ability) {
        super(ability);
    }

    @Override
    public SwordOfDungeonsAndDragonsAbility copy() {
        return new SwordOfDungeonsAndDragonsAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedPlayerEvent damageEvent = (DamagedPlayerEvent)event;
        Permanent p = game.getPermanent(event.getSourceId());
        if (damageEvent.isCombatDamage() && p != null && p.getAttachments().contains(this.getSourceId())) {
            for (Effect effect : this.getEffects()) {
                effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever equipped creature deals combat damage to a player, you create a 4/4 gold Dragon creature token with flying and roll a d20. If you roll a 20, repeat this process.";
    }
}

class SwordOfDungeonsAndDragonsEffect extends OneShotEffect {
    
    public SwordOfDungeonsAndDragonsEffect() {
        super(Outcome.Benefit);
    }

    public SwordOfDungeonsAndDragonsEffect(final SwordOfDungeonsAndDragonsEffect effect) {
        super(effect);
    }

    @Override
    public SwordOfDungeonsAndDragonsEffect copy() {
        return new SwordOfDungeonsAndDragonsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int count = 1;
            int dice = (int)(Math.random()*20+1);
            while (dice == 20) {
                count += 1;
                dice = (int)(Math.random()*20+1);
            }
            return new CreateTokenEffect(new DragonTokenGold(), count).apply(game, source);
        }
        return false;
    }
}