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
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.token.Token;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author jeffwadsworth
 */
public class VastwoodAnimist extends CardImpl {

    public VastwoodAnimist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add("Elf");
        this.subtype.add("Shaman");
        this.subtype.add("Ally");

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Target land you control becomes an X/X Elemental creature until end of turn, where X is the number of Allies you control. It's still a land.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new VastwoodAnimistEffect(), new TapSourceCost());
        ability.addTarget(new TargetControlledPermanent(new FilterControlledLandPermanent()));
        this.addAbility(ability);
    }

    public VastwoodAnimist(final VastwoodAnimist card) {
        super(card);
    }

    @Override
    public VastwoodAnimist copy() {
        return new VastwoodAnimist(this);
    }
}

class VastwoodAnimistEffect extends OneShotEffect {

    final static FilterControlledPermanent filterAllies = new FilterControlledPermanent("allies you control");

    static {
        filterAllies.add(new SubtypePredicate(SubType.ALLY));
    }

    public VastwoodAnimistEffect() {
        super(Outcome.Benefit);
        this.staticText = "Target land you control becomes an X/X Elemental creature until end of turn, where X is the number of Allies you control. It's still a land.";
    }

    public VastwoodAnimistEffect(final VastwoodAnimistEffect effect) {
        super(effect);
    }

    @Override
    public VastwoodAnimistEffect copy() {
        return new VastwoodAnimistEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = new PermanentsOnBattlefieldCount(filterAllies).calculate(game, source, this);
        ContinuousEffect effect = new BecomesCreatureTargetEffect(new VastwoodAnimistElementalToken(amount), false, true, Duration.EndOfTurn);
        effect.setTargetPointer(targetPointer);
        game.addEffect(effect, source);
        return false;
    }
}

class VastwoodAnimistElementalToken extends Token {

    VastwoodAnimistElementalToken(int amount) {
        super("", "X/X Elemental creature, where X is the number of Allies you control");
        cardType.add(CardType.CREATURE);
        subtype.add("Elemental");
        power = new MageInt(amount);
        toughness = new MageInt(amount);
    }
}
