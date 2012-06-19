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

package mage.sets.newphyrexia;

import java.util.UUID;

import mage.Constants.AttachmentType;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continious.BoostEquippedEffect;
import mage.abilities.effects.common.continious.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.filter.Filter;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author Loki
 */
public class SwordOfWarAndPeace extends CardImpl<SwordOfWarAndPeace> {
    private static final FilterCard filter = new FilterCard("red and from white");

    static {
        filter.setUseColor(true);
        filter.getColor().setRed(true);
        filter.getColor().setWhite(true);
        filter.setScopeColor(Filter.ComparisonScope.Any);
    }

    public SwordOfWarAndPeace (UUID ownerId) {
        super(ownerId, 161, "Sword of War and Peace", Rarity.MYTHIC, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.expansionSetCode = "NPH";
        this.subtype.add("Equipment");
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(2)));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(2, 2)));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(new ProtectionAbility(filter), AttachmentType.EQUIPMENT)));
        this.addAbility(new SwordOfWarAndPeaceAbility());
    }

    public SwordOfWarAndPeace (final SwordOfWarAndPeace card) {
        super(card);
    }

    @Override
    public SwordOfWarAndPeace copy() {
        return new SwordOfWarAndPeace(this);
    }

}

class SwordOfWarAndPeaceAbility extends TriggeredAbilityImpl<SwordOfWarAndPeaceAbility> {

    public SwordOfWarAndPeaceAbility() {
        super(Zone.BATTLEFIELD, new SwordOfWarAndPeaceDamageEffect());
        this.addEffect(new GainLifeEffect(new CardsInControllerHandCount()));
        this.addTarget(new TargetPlayer());
    }

    public SwordOfWarAndPeaceAbility(final SwordOfWarAndPeaceAbility ability) {
        super(ability);
    }

    @Override
    public SwordOfWarAndPeaceAbility copy() {
        return new SwordOfWarAndPeaceAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event instanceof DamagedPlayerEvent) {
            DamagedPlayerEvent damageEvent = (DamagedPlayerEvent)event;
            Permanent p = game.getPermanent(event.getSourceId());
            if (damageEvent.isCombatDamage() && p != null && p.getAttachments().contains(this.getSourceId())) {
                getTargets().get(0).add(event.getPlayerId(), game);
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever equipped creature deals combat damage to a player, {this} deals damage to that player equal to the number of cards in his or her hand and you gain 1 life for each card in your hand.";
    }
}

class SwordOfWarAndPeaceDamageEffect extends OneShotEffect<SwordOfWarAndPeaceDamageEffect> {
    SwordOfWarAndPeaceDamageEffect() {
        super(Outcome.Damage);
        staticText = "{this} deals damage to that player equal to the number of cards in his or her hand";
    }

    SwordOfWarAndPeaceDamageEffect(final SwordOfWarAndPeaceDamageEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player target = game.getPlayer(source.getFirstTarget());
        if (target != null) {
            target.damage(target.getHand().size(), source.getSourceId(), game, false, true);
        }
        return false;
    }

    @Override
    public SwordOfWarAndPeaceDamageEffect copy() {
        return new SwordOfWarAndPeaceDamageEffect(this);
    }

}