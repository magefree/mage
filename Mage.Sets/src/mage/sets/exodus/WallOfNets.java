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
package mage.sets.exodus;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EndOfCombatTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ExileAllEffect;
import mage.abilities.effects.common.ReturnFromExileEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.BlockedByIdPredicate;

/**
 *
 * @author LoneFox
 */
public class WallOfNets extends CardImpl {

    public WallOfNets(UUID ownerId) {
        super(ownerId, 24, "Wall of Nets", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");
        this.expansionSetCode = "EXO";
        this.subtype.add("Wall");
        this.power = new MageInt(0);
        this.toughness = new MageInt(7);

        // Defender
        this.addAbility(DefenderAbility.getInstance());
        // At end of combat, exile all creatures blocked by Wall of Nets.
        FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures blocked by {this}");
        filter.add(new BlockedByIdPredicate(this.getId()));
        this.addAbility(new EndOfCombatTriggeredAbility(new ExileAllEffect(filter, this.getId(), this.getIdName()), false));
        // When Wall of Nets leaves the battlefield, return all cards exiled with Wall of Nets to the battlefield under their owners' control.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new ReturnFromExileEffect(this.getId(),
            Zone.BATTLEFIELD, "return all cards exiled with {this} to the battlefield under their owners' control"), false));
    }

    public WallOfNets(final WallOfNets card) {
        super(card);
    }

    @Override
    public WallOfNets copy() {
        return new WallOfNets(this);
    }
}
