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
package mage.sets.zendikar;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
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
public class RelicCrush extends CardImpl<RelicCrush> {
    
    private final static FilterPermanent filter = new FilterPermanent("artifact or enchantment");
    
    static {
        filter.add(Predicates.or(
                (new CardTypePredicate(CardType.ARTIFACT)),
                (new CardTypePredicate(CardType.ENCHANTMENT))));
    }
    
    public RelicCrush(UUID ownerId) {
        super(ownerId, 179, "Relic Crush", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{4}{G}");
        this.expansionSetCode = "ZEN";

        this.color.setGreen(true);

        // Destroy target artifact or enchantment and up to one other target artifact or enchantment.
        this.getSpellAbility().addEffect(new RelicCrushEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addTarget(new TargetPermanent(0, 1, filter, false));
        
    }

    public RelicCrush(final RelicCrush card) {
        super(card);
    }

    @Override
    public RelicCrush copy() {
        return new RelicCrush(this);
    }
}

class RelicCrushEffect extends OneShotEffect<RelicCrushEffect> {

    public RelicCrushEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy target artifact or enchantment and up to one other target artifact or enchantment";
    }

    public RelicCrushEffect(final RelicCrushEffect effect) {
        super(effect);
    }

    @Override
    public RelicCrushEffect copy() {
        return new RelicCrushEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent firstTarget = game.getPermanent(source.getFirstTarget());
        Permanent secondTarget = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (firstTarget != null) {
            firstTarget.destroy(id, game, false);
        }
        if (secondTarget != null) {
            return secondTarget.destroy(id, game, false);
        }
        return true;
    }
}
