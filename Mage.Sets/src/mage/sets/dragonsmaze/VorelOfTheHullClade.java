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
package mage.sets.dragonsmaze;

import java.util.UUID;

import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.Zone;
import mage.counters.Counter;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

/**
 *
 * @author jeffwadsworth
 */
public class VorelOfTheHullClade extends CardImpl<VorelOfTheHullClade> {
    
    final static private FilterPermanent filter = new FilterPermanent("artifact, creature, or land");
    
    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.CREATURE),
                new CardTypePredicate(CardType.LAND)));
    }

    public VorelOfTheHullClade(UUID ownerId) {
        super(ownerId, 115, "Vorel of the Hull Clade", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{G}{U}");
        this.expansionSetCode = "DGM";
        this.supertype.add("Legendary");
        this.subtype.add("Human");
        this.subtype.add("Merfolk");

        this.color.setBlue(true);
        this.color.setGreen(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // {G}{U}, {tap}: For each counter on target artifact, creature, or land, put another of those counters on that permanent.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new VorelOfTheHullCladeEffect(), new ManaCostsImpl("{G}{U}"));
        ability.addTarget(new TargetPermanent(filter));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
        
    }

    public VorelOfTheHullClade(final VorelOfTheHullClade card) {
        super(card);
    }

    @Override
    public VorelOfTheHullClade copy() {
        return new VorelOfTheHullClade(this);
    }
}

class VorelOfTheHullCladeEffect extends OneShotEffect<VorelOfTheHullCladeEffect> {

    public VorelOfTheHullCladeEffect() {
        super(Outcome.Benefit);
        staticText = "For each counter on target artifact, creature, or land, put another of those counters on that permanent";
    }

    public VorelOfTheHullCladeEffect(VorelOfTheHullCladeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = game.getPermanent(source.getFirstTarget());
        if (target == null) {
            return false;
        }
        for (Counter counter : target.getCounters().values()) {
            Counter newCounter = new Counter(counter.getName(), 1);
            target.addCounters(newCounter, game);
            System.out.println("The target and counter type added is " + target.getName() + counter.getName());
        }
        return true;
    }

    @Override
    public VorelOfTheHullCladeEffect copy() {
        return new VorelOfTheHullCladeEffect(this);
    }

}

