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
package mage.sets.magic2014;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.continious.BoostTargetEffect;
import mage.abilities.effects.common.continious.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author jeffwadsworth
 */
public class OgreBattledriver extends CardImpl<OgreBattledriver> {
    
    private final static FilterCreaturePermanent filter = new FilterCreaturePermanent("another creature");
    
    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(new AnotherPredicate());
    }
    
    private String rule = "Whenever another creature enters the battlefield under your control, that creature gets +2/+0 and gains haste until end of turn.";

    public OgreBattledriver(UUID ownerId) {
        super(ownerId, 148, "Ogre Battledriver", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");
        this.expansionSetCode = "M14";
        this.subtype.add("Ogre");
        this.subtype.add("Warrior");

        this.color.setRed(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever another creature enters the battlefield under your control, that creature gets +2/+0 and gains haste until end of turn.
        Ability ability = new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD, new BoostTargetEffect(2, 0, Duration.EndOfTurn), filter, false, true, rule, true);
        ability.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn));
        this.addAbility(ability);
        
    }

    public OgreBattledriver(final OgreBattledriver card) {
        super(card);
    }

    @Override
    public OgreBattledriver copy() {
        return new OgreBattledriver(this);
    }
}
