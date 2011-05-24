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

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.CardsInControlledPlayerHandCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DiscardTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.UntapAllLandsControllerEffect;
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
public class SwordofWarandPeace extends CardImpl<SwordofWarandPeace> {
    private static FilterCard filter = new FilterCard("red and from white");

    static {
        filter.setUseColor(true);
        filter.getColor().setRed(true);
        filter.getColor().setWhite(true);
        filter.setScopeColor(Filter.ComparisonScope.Any);
    }

    public SwordofWarandPeace (UUID ownerId) {
        super(ownerId, 161, "Sword of War and Peace", Rarity.MYTHIC, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.expansionSetCode = "NPH";
        this.subtype.add("Equipment");
        this.addAbility(new EquipAbility(Constants.Outcome.AddAbility, new GenericManaCost(2)));
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new BoostEquippedEffect(2, 2)));
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new GainAbilityAttachedEffect(new ProtectionAbility(filter), Constants.AttachmentType.EQUIPMENT)));
        this.addAbility(new SwordofWarandPeaceAbility());
    }

    public SwordofWarandPeace (final SwordofWarandPeace card) {
        super(card);
    }

    @Override
    public SwordofWarandPeace copy() {
        return new SwordofWarandPeace(this);
    }

}

class SwordofWarandPeaceAbility extends TriggeredAbilityImpl<SwordofWarandPeaceAbility> {

    public SwordofWarandPeaceAbility() {
        super(Constants.Zone.BATTLEFIELD, new SwordofWarandPeaceDamageEffect());
        this.addEffect(new GainLifeEffect(new CardsInControlledPlayerHandCount()));
        this.addTarget(new TargetPlayer());
    }

    public SwordofWarandPeaceAbility(final SwordofWarandPeaceAbility ability) {
        super(ability);
    }

    @Override
    public SwordofWarandPeaceAbility copy() {
        return new SwordofWarandPeaceAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event instanceof DamagedPlayerEvent) {
            DamagedPlayerEvent damageEvent = (DamagedPlayerEvent)event;
            Permanent p = game.getPermanent(event.getSourceId());
            if (damageEvent.isCombatDamage() && p != null && p.getAttachments().contains(this.getSourceId())) {
                this.targets.get(0).add(event.getPlayerId(), game);
			    return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever equipped creature deals combat damage to a player, Sword of War and Peace deals damage to that player equal to the number of cards in his or her hand and you gain 1 life for each card in your hand.";
    }
}

class SwordofWarandPeaceDamageEffect extends OneShotEffect<SwordofWarandPeaceDamageEffect> {
    SwordofWarandPeaceDamageEffect() {
        super(Constants.Outcome.Damage);
    }

    SwordofWarandPeaceDamageEffect(final SwordofWarandPeaceDamageEffect effect) {
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
    public SwordofWarandPeaceDamageEffect copy() {
        return new SwordofWarandPeaceDamageEffect(this);
    }

    @Override
    public String getText(Ability source) {
        return "Sword of War and Peace deals damage to that player equal to the number of cards in his or her hand";
    }
}