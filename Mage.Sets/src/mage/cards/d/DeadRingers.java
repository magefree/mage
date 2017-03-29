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
package mage.cards.d;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author LoneFox
 */
public class DeadRingers extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonblack creature");

    static {
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.BLACK)));
    }

    public DeadRingers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{B}");

        // Destroy two target nonblack creatures unless either one is a color the other isn't. They can't be regenerated.
        this.getSpellAbility().addEffect(new DeadRingersEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(2, 2, filter, false));
    }

    public DeadRingers(final DeadRingers card) {
        super(card);
    }

    @Override
    public DeadRingers copy() {
        return new DeadRingers(this);
    }
}

class DeadRingersEffect extends DestroyTargetEffect {

    public DeadRingersEffect() {
        super(true);
        staticText = "Destroy two target nonblack creatures unless either one is a color the other isn't. They can't be regenerated.";
    }

    public DeadRingersEffect(final DeadRingersEffect effect) {
        super(effect);
    }

    @Override
    public DeadRingersEffect copy() {
        return new DeadRingersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Target target = source.getTargets().get(0);
        Permanent first = game.getPermanentOrLKIBattlefield(target.getTargets().get(0));
        Permanent second = game.getPermanentOrLKIBattlefield(target.getTargets().get(1));
        if(first != null && second != null && first.getColor(game).equals(second.getColor(game))) {
            return super.apply(game, source);
        }
        return false;
    }
}
