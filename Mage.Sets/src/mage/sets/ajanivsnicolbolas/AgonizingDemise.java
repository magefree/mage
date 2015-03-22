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
package mage.sets.ajanivsnicolbolas;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author FenrisulfrX
 */
public class AgonizingDemise extends CardImpl {
    
    private static final FilterCreaturePermanent filterNonBlackCreature = new FilterCreaturePermanent("nonblack creature");
    static {
        filterNonBlackCreature.add(Predicates.not(new ColorPredicate(ObjectColor.BLACK)));
    }

    public AgonizingDemise(UUID ownerId) {
        super(ownerId, 66, "Agonizing Demise", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{3}{B}");
        this.expansionSetCode = "DDH";

        // Kicker {1}{R}
        this.addAbility(new KickerAbility("{1}{R}"));
        
        // Destroy target nonblack creature. It can't be regenerated.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filterNonBlackCreature));
        
        //If Agonizing Demise was kicked, it deals damage equal to that creature's power to the creature's controller.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new AgonizingDemiseEffect(),
                KickedCondition.getInstance(),
                "If {this} was kicked, it deals damage equal to that creature's power to the creature's controller."));
        
    }

    public AgonizingDemise(final AgonizingDemise card) {
        super(card);
    }

    @Override
    public AgonizingDemise copy() {
        return new AgonizingDemise(this);
    }
}

class AgonizingDemiseEffect extends OneShotEffect {
    
    public AgonizingDemiseEffect() {
        super(Outcome.Damage);
    }
    
    public AgonizingDemiseEffect(final AgonizingDemiseEffect effect) {
        super(effect);
    }
    
    @Override
    public AgonizingDemiseEffect copy() {
        return new AgonizingDemiseEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(this.getTargetPointer().getFirst(game, source));
        if(permanent != null) {
            Player controller = game.getPlayer(permanent.getControllerId());
            if(controller != null) {
                int amount = permanent.getPower().getValue();
                controller.damage(amount, source.getSourceId(), game, false, true);
                return true;
            }
        }
        return false;
    }
}
