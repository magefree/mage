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
package mage.sets.fifthedition;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.TargetSpell;

/**
 *
 * @author emerald000
 */
public class Hydroblast extends CardImpl {

    public Hydroblast(UUID ownerId) {
        super(ownerId, 94, "Hydroblast", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{U}");
        this.expansionSetCode = "5ED";


        // Choose one - Counter target spell if it's red;
        this.getSpellAbility().addEffect(new HydroblastCounterEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
        
        // or destroy target permanent if it's red.
        Mode mode = new Mode();
        mode.getEffects().add(new HydroblastDestroyEffect());
        mode.getTargets().add(new TargetPermanent());
        this.getSpellAbility().addMode(mode);
    }

    public Hydroblast(final Hydroblast card) {
        super(card);
    }

    @Override
    public Hydroblast copy() {
        return new Hydroblast(this);
    }
}

class HydroblastCounterEffect extends OneShotEffect {

    HydroblastCounterEffect() {
        super(Outcome.Detriment);
    }

    HydroblastCounterEffect(final HydroblastCounterEffect effect) {
        super(effect);
    }

    @Override
    public HydroblastCounterEffect copy() {
        return new HydroblastCounterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (game.getStack().getSpell(source.getFirstTarget()).getColor().isRed()) {
            game.getStack().counter(source.getFirstTarget(), source.getSourceId(), game);
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        return "Counter target spell if it's red";
    }
}

class HydroblastDestroyEffect extends OneShotEffect {

    HydroblastDestroyEffect() {
        super(Outcome.DestroyPermanent);
    }

    HydroblastDestroyEffect(final HydroblastDestroyEffect effect) {
        super(effect);
    }

    @Override
    public HydroblastDestroyEffect copy() {
        return new HydroblastDestroyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getTargets().getFirstTarget());
        if (permanent != null && permanent.getColor().isRed()) {
            permanent.destroy(source.getSourceId(), game, false);
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        return "destroy target permanent if it's red";
    }
}
