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
package mage.sets.ninthedition;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Layer;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.SubLayer;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Plopman
 */
public class SwarmOfRats extends CardImpl<SwarmOfRats> {

    public SwarmOfRats(UUID ownerId) {
        super(ownerId, 166, "Swarm of Rats", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.expansionSetCode = "9ED";
        this.subtype.add("Rat");

        this.color.setBlack(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Swarm of Rats's power is equal to the number of Rats you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SwarmOfRatsEffect()));
    }

    public SwarmOfRats(final SwarmOfRats card) {
        super(card);
    }

    @Override
    public SwarmOfRats copy() {
        return new SwarmOfRats(this);
    }
}

class SwarmOfRatsEffect extends ContinuousEffectImpl<SwarmOfRatsEffect> {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("Rats you control");
    static{
        filter.add(new SubtypePredicate("Rat"));
    }
    
    private DynamicValue amount;

    public SwarmOfRatsEffect() {
        super(Duration.EndOfGame, Layer.PTChangingEffects_7, SubLayer.SetPT_7b, Outcome.BoostCreature);
        this.amount = new PermanentsOnBattlefieldCount(filter);
        staticText = "{this}'s power is equal to the number of Rats you control";
    }


    public SwarmOfRatsEffect(final SwarmOfRatsEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public SwarmOfRatsEffect copy() {
        return new SwarmOfRatsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = game.getPermanent(source.getSourceId());
        if (target != null) {
            int value = amount.calculate(game, source);
            target.getPower().setValue(value);
            return true;
        }
        return false;
    }

}
