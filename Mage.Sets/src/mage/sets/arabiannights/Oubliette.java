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
package mage.sets.arabiannights;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.abilities.effects.common.ReturnFromExileForSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.Target;
import mage.target.TargetPermanent;

/**
 *
 * @author MarcoMarin 
 */
public class Oubliette extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("target creature");
    
    public Oubliette(UUID ownerId) {
        super(ownerId, 11, "Oubliette", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}{B}");
        this.expansionSetCode = "ARN";

        // When Oubliette enters the battlefield, exile target creature and all Auras attached to it. Note the number and kind of counters that were on that creature.
        Ability ability1 = new EntersBattlefieldTriggeredAbility(new ExileTargetForSourceEffect(), false);
        Target target = new TargetPermanent(filter);
        ability1.addTarget(target);
        this.addAbility(ability1);
        
        // When Oubliette leaves the battlefield, return the exiled card to the battlefield under its owner's control tapped with the noted number and kind of counters on it. If you do, return the exiled Aura cards to the battlefield under their owner's control attached to that permanent.
        Ability ability2 = new LeavesBattlefieldTriggeredAbility(new ReturnFromExileForSourceEffect(Zone.BATTLEFIELD), false);
        this.addAbility(ability2);
    }

    public Oubliette(final Oubliette card) {
        super(card);
    }

    @Override
    public Oubliette copy() {
        return new Oubliette(this);
    }
}
