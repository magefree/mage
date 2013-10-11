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
package mage.sets.odyssey;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FirstTargetPointer;

/**
 *
 * @author cbt33
 */
public class GhastlyDemise extends CardImpl<GhastlyDemise> {
    
        private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature if its tougness is less than the number of cards in your graveyard");

        
    static{
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.BLACK)));
        }
    
    public GhastlyDemise(UUID ownerId) {
        super(ownerId, 139, "Ghastly Demise", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{B}");
        this.expansionSetCode = "ODY";

        this.color.setBlack(true);

        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
        this.getSpellAbility().addEffect(new GhastlyDemiseEffect(false));
    }

    public GhastlyDemise(final GhastlyDemise card) {
        super(card);
    }

    @Override
    public GhastlyDemise copy() {
        return new GhastlyDemise(this);
    }
}

class GhastlyDemiseEffect extends OneShotEffect<GhastlyDemiseEffect> {
    
protected boolean noRegen;

    public GhastlyDemiseEffect(String ruleText) {
        this(false);
        ruleText = "Destroy target nonblack creature if its toughness is less than or equal to the number of cards in your graveyard.";
        staticText = ruleText;
    }

    public GhastlyDemiseEffect(boolean noRegen) {
        super(Outcome.DestroyPermanent);
        this.noRegen = noRegen;
    }

    public GhastlyDemiseEffect(final GhastlyDemiseEffect effect) {
        super(effect);
        this.noRegen = effect.noRegen;
    }

    @Override
    public GhastlyDemiseEffect copy() {
        return new GhastlyDemiseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int affectedTargets = 0;
        if (source.getTargets().size() > 1 && targetPointer instanceof FirstTargetPointer) { // for Rain of Thorns
            for (Target target : source.getTargets()) {
                for (UUID permanentId : target.getTargets()) {
                    Permanent permanent = game.getPermanent(permanentId);
                    if (permanent != null && permanent.getToughness().getValue() <= game.getPlayer(source.getControllerId()).getGraveyard().count(new FilterCard(), game)) {
                        permanent.destroy(source.getId(), game, noRegen);
                        affectedTargets++;
                    }
                }
            }
        }
        else if (targetPointer.getTargets(game, source).size() > 0) {
            for (UUID permanentId : targetPointer.getTargets(game, source)) {
                Permanent permanent = game.getPermanent(permanentId);
                if (permanent != null && permanent.getToughness().getValue() <= game.getPlayer(source.getControllerId()).getGraveyard().count(new FilterCard(), game)) {
                    permanent.destroy(source.getId(), game, noRegen);
                    affectedTargets++;
                }
            }
        }
        return affectedTargets > 0;
    }

        @Override
    public String getText(Mode mode) {
        return staticText;
}
        
        
        
}
