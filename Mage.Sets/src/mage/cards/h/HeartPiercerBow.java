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
package mage.cards.h;

import java.util.UUID;
import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class HeartPiercerBow extends CardImpl {

    public HeartPiercerBow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");
        this.subtype.add("Equipment");

        // Whenever equipped creature attacks, Heart-Piercer Bow deals 1 damage to target creature defending player controls.
        this.addAbility(new HeartPiercerBowAbility());

        // Equip {1}
        this.addAbility(new EquipAbility(Outcome.Benefit, new GenericManaCost(1)));
    }

    public HeartPiercerBow(final HeartPiercerBow card) {
        super(card);
    }



    @Override
    public HeartPiercerBow copy() {
        return new HeartPiercerBow(this);
    }
}

class HeartPiercerBowAbility extends AttacksAttachedTriggeredAbility {

    public HeartPiercerBowAbility() {
        super(new DamageTargetEffect(1), AttachmentType.EQUIPMENT, false);
    }

    public HeartPiercerBowAbility(final HeartPiercerBowAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (super.checkTrigger(event, game)) {
            Permanent equipment = game.getPermanent(getSourceId());
            if (equipment != null && equipment.getAttachedTo() != null) {
                FilterCreaturePermanent filter = new FilterCreaturePermanent("creature defending player controls");
                UUID defenderId = game.getCombat().getDefendingPlayerId(equipment.getAttachedTo(), game);
                if (defenderId != null) {
                    filter.add(new ControllerIdPredicate(defenderId));
                    this.getTargets().clear();
                    TargetCreaturePermanent target = new TargetCreaturePermanent(filter);
                    this.addTarget(target);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever equipped creature attacks, {this} deals 1 damage to target creature defending player controls.";
    }

    @Override
    public HeartPiercerBowAbility copy() {
        return new HeartPiercerBowAbility(this);
    }
}