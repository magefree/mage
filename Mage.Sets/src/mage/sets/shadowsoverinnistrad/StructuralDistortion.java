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
package mage.sets.shadowsoverinnistrad;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;

/**
 *
 * @author fireshoes
 */
public class StructuralDistortion extends CardImpl {
    
    private static final FilterPermanent filter = new FilterPermanent("artifact or land");
    
    static {
        filter.add(Predicates.or(new CardTypePredicate(CardType.ARTIFACT),new CardTypePredicate(CardType.LAND)));
    }

    public StructuralDistortion(UUID ownerId) {
        super(ownerId, 185, "Structural Distortion", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{3}{R}");
        this.expansionSetCode = "SOI";

        // Exile target artifact or land. Structural Distortion deals 2 damage to that permanent's controller.
        this.getSpellAbility().addEffect(new StructuralDistortionEffect());
        Target target = new TargetPermanent(filter);
        this.getSpellAbility().addTarget(target);
    }

    public StructuralDistortion(final StructuralDistortion card) {
        super(card);
    }

    @Override
    public StructuralDistortion copy() {
        return new StructuralDistortion(this);
    }
}

class StructuralDistortionEffect extends OneShotEffect {

    public StructuralDistortionEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Exile target artifact or land. {this} deals 2 damage to that permanent's controller";
    }

    public StructuralDistortionEffect(final StructuralDistortionEffect effect) {
        super(effect);
    }

    @Override
    public StructuralDistortionEffect copy() {
        return new StructuralDistortionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        if (permanent != null) {
            permanent.moveToExile(source.getSourceId(), null, source.getSourceId(), game);
            Player permController = game.getPlayer(permanent.getControllerId());
            if (permController != null) {
                permController.damage(2, source.getSourceId(), game, false, true);
                return true;
            }
        }
        return false;
    }
}
