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
package mage.cards.o;

import java.util.ArrayList;
import java.util.UUID;

import mage.constants.CardType;
import mage.ObjectColor;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.token.Token;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author Plopman
 */
public class OozeGarden extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("non-Ooze creature");
    static{
        filter.add(Predicates.not(new SubtypePredicate(SubType.OOZE)));
    }
    public OozeGarden(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{G}");


        // {1}{G}, Sacrifice a non-Ooze creature: Create an X/X green Ooze creature token, where X is the sacrificed creature's power. Activate this ability only any time you could cast a sorcery.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new OozeGardenCreateTokenEffect(), new ManaCostsImpl("{1}{G}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(1, 1, filter, true)));
        this.addAbility(ability);
    }

    public OozeGarden(final OozeGarden card) {
        super(card);
    }

    @Override
    public OozeGarden copy() {
        return new OozeGarden(this);
    }
}

class OozeGardenCreateTokenEffect extends OneShotEffect {

    public OozeGardenCreateTokenEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Create an X/X green Ooze creature token, where X is the sacrificed creature's power";
    }

    public OozeGardenCreateTokenEffect(final OozeGardenCreateTokenEffect effect) {
        super(effect);
    }

    @Override
    public OozeGardenCreateTokenEffect copy() {
        return new OozeGardenCreateTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int value = 0;
        for(Cost cost : source.getCosts()){
            if(cost instanceof SacrificeTargetCost){
                value = ((SacrificeTargetCost)cost).getPermanents().get(0).getPower().getValue();
            }
        }
        ArrayList<String> list = new ArrayList<>();
        list.add("Ooze");
        Token token = new Token("Ooze", "X/X green Ooze creature token, where X is the sacrificed creature's power", ObjectColor.GREEN, list, value, value, new AbilitiesImpl<>()) {
           
      
        };
        token.getAbilities().newId(); // neccessary if token has ability like DevourAbility()
        token.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId());
        return true;
    }


}

