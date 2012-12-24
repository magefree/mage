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
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterArtifactPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author jonubuu
 */
public class Glimmervoid extends CardImpl<Glimmervoid> {

    public Glimmervoid(UUID ownerId) {
        super(ownerId, 281, "Glimmervoid", Rarity.RARE, new CardType[]{CardType.LAND}, "");
        this.expansionSetCode = "MRD";

        // At the beginning of the end step, if you control no artifacts, sacrifice Glimmervoid.
        this.addAbility(new GlimmervoidTriggeredAbility());
        // {tap}: Add one mana of any color to your mana pool.
        this.addAbility(new AnyColorManaAbility());
    }

    public Glimmervoid(final Glimmervoid card) {
        super(card);
    }

    @Override
    public Glimmervoid copy() {
        return new Glimmervoid(this);
    }
}

class GlimmervoidTriggeredAbility extends TriggeredAbilityImpl<GlimmervoidTriggeredAbility> {

    GlimmervoidTriggeredAbility() {
        super(Constants.Zone.BATTLEFIELD, new SacrificeSourceEffect());
    }

    GlimmervoidTriggeredAbility(final GlimmervoidTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GlimmervoidTriggeredAbility copy() {
        return new GlimmervoidTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.END_TURN_STEP_PRE) {
            FilterArtifactPermanent filter = new FilterArtifactPermanent();
            if (!game.getBattlefield().contains(filter, controllerId, 1, game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "At the beginning of the end step, if you control no Artifacts, sacrifice {this}.";
    }
}
