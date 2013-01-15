/*
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
package mage.sets.gatecrash;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.continious.GainAbilityAllEffect;
import mage.abilities.keyword.BattalionAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;

/**
 *
 * @author Plopman
 */
public class LegionLoyalist extends CardImpl<LegionLoyalist> {

    public LegionLoyalist(UUID ownerId) {
        super(ownerId, 97, "Legion Loyalist", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{R}");
        this.expansionSetCode = "GTC";
        this.subtype.add("Goblin");
        this.subtype.add("Soldier");

        this.color.setRed(true);
        
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        //Haste
        this.addAbility(HasteAbility.getInstance());
        //Battalion - Whenever Legion Loyalist and at least two other creatures attack, 
        //creatures you control gain first strike and trample until end of turn and can't be blocked by tokens this turn.
        Ability ability = new BattalionAbility(new GainAbilityAllEffect(FirstStrikeAbility.getInstance(), Constants.Duration.EndOfTurn, new FilterControlledCreaturePermanent()));
        ability.addEffect(new GainAbilityAllEffect(TrampleAbility.getInstance(), Constants.Duration.EndOfTurn, new FilterControlledCreaturePermanent()));
        ability.addEffect(new CantBeBlockedByTokenEffect());
        this.addAbility(ability);
    }

    public LegionLoyalist(final LegionLoyalist card) {
        super(card);
    }

    @Override
    public LegionLoyalist copy() {
        return new LegionLoyalist(this);
    }
}

class CantBeBlockedByTokenEffect extends RestrictionEffect<CantBeBlockedByTokenEffect> {

    public CantBeBlockedByTokenEffect() {
        super(Constants.Duration.EndOfTurn);
        staticText = "Creatures you control can't be blocked by tokens this turn";
    }

    public CantBeBlockedByTokenEffect(final CantBeBlockedByTokenEffect effect) {
        super(effect);
    }
    
    @Override
    public void init(Ability source, Game game) {
        affectedObjectsSet = true;
        for (Permanent perm: game.getBattlefield().getActivePermanents(new FilterControlledCreaturePermanent(), source.getControllerId(), source.getSourceId(), game)) {
            objects.add(perm.getId());
        }
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (objects.contains(permanent.getId())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean canBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game) {
        if (blocker instanceof PermanentToken) {
            return false;
        }
        return true;
    }

    @Override
    public CantBeBlockedByTokenEffect copy() {
        return new CantBeBlockedByTokenEffect(this);
    }
}

