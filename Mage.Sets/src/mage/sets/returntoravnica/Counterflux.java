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
package mage.sets.returntoravnica;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import mage.Constants;
import mage.Constants.Outcome;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CantCounterSourceEffect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.keyword.OverloadAbility;
import mage.cards.CardImpl;
import mage.filter.FilterSpell;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.target.TargetSpell;


/**
 *
 * @author LevelX2
 */
public class Counterflux extends CardImpl<mage.sets.returntoravnica.Counterflux> {

    private static final FilterSpell filter = new FilterSpell("spell you don't control");

    static {
        filter.add(new ControllerPredicate(Constants.TargetController.NOT_YOU));
    }

    public Counterflux(UUID ownerId) {
        super(ownerId, 153, "Counterflux", Constants.Rarity.RARE, new Constants.CardType[]{Constants.CardType.INSTANT}, "{U}{U}{R}");
        this.expansionSetCode = "RTR";

        this.color.setBlue(true);
        this.color.setRed(true);

        // Counterflux can't be countered by spells or abilities.
        this.getSpellAbility().addEffect(new CantCounterSourceEffect());

        // Counter target spell you don't control.
        this.getSpellAbility().addTarget(new TargetSpell(filter));
        this.getSpellAbility().addEffect(new CounterTargetEffect());

        // Overload {1}{U}{U}{R} (You may cast this spell for its overload cost. If you do, change its text by replacing all instances of "target" with "each.")
        this.addAbility(new OverloadAbility(this, new CounterfluxEffect(), new ManaCostsImpl("{1}{U}{U}{R}")));
    }

    public Counterflux(final mage.sets.returntoravnica.Counterflux card) {
        super(card);
    }

    @Override
    public mage.sets.returntoravnica.Counterflux copy() {
        return new mage.sets.returntoravnica.Counterflux(this);
    }
}

class CounterfluxEffect extends OneShotEffect<CounterfluxEffect> {

    public CounterfluxEffect() {
        super(Outcome.Detriment);
        staticText = "Counter each spell you don't control.";

    }

    public CounterfluxEffect(final CounterfluxEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {

        List<Spell> spellsToCounter = new LinkedList<Spell>();
        for (StackObject stackObject : game.getStack()) {
            if (stackObject instanceof Spell && !stackObject.getControllerId().equals(source.getControllerId())) {
                spellsToCounter.add((Spell) stackObject);
            }
        }
        for (Spell spell : spellsToCounter) {
            game.getStack().counter(spell.getId(), source.getSourceId(), game);
        }
        return true;
    }

    @Override
    public CounterfluxEffect copy() {
        return new CounterfluxEffect(this);
    }

}