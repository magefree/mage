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
package mage.sets.iceage;

import java.util.UUID;

import mage.constants.CardType;
import mage.constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.TargetSpell;

/**
 *
 * @author Plopman
 */
public class Pyroblast extends CardImpl<Pyroblast> {
    
    public Pyroblast(UUID ownerId) {
        super(ownerId, 213, "Pyroblast", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{R}");
        this.expansionSetCode = "ICE";

        this.color.setRed(true);

        // Choose one - Counter target spell if it's blue; or destroy target permanent if it's blue.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
        
        Mode mode = new Mode();
        mode.getEffects().add(new DestroyTargetEffect());
        mode.getTargets().add(new TargetPermanent());
        
        this.getSpellAbility().addMode(mode);
    }

    public Pyroblast(final Pyroblast card) {
        super(card);
    }

    @Override
    public Pyroblast copy() {
        return new Pyroblast(this);
    }
}

class CounterTargetEffect extends OneShotEffect<CounterTargetEffect> {

    public CounterTargetEffect() {
        super(Outcome.Detriment);
    }

    public CounterTargetEffect(final CounterTargetEffect effect) {
        super(effect);
    }

    @Override
    public CounterTargetEffect copy() {
        return new CounterTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if(game.getStack().getSpell(source.getFirstTarget()).getColor().isBlue()){
            game.getStack().counter(source.getFirstTarget(), source.getSourceId(), game);
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        return "Counter target spell if it's blue";
    }

}


class DestroyTargetEffect extends OneShotEffect<DestroyTargetEffect> {


    public DestroyTargetEffect() {
        super(Outcome.DestroyPermanent);
    }

    public DestroyTargetEffect(final DestroyTargetEffect effect) {
        super(effect);
    }

    @Override
    public DestroyTargetEffect copy() {
        return new DestroyTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getTargets().getFirstTarget());
        if (permanent != null && permanent.getColor().isBlue()) {
            permanent.destroy(source.getId(), game, false);
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        return "Destroy target permanent if it's blue";
    }

}
