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
package mage.sets.visions;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.TargetController;
import mage.abilities.Ability;
import mage.abilities.costs.AlternativeCostImpl;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author jeffwadsworth

 */
public class Fireblast extends CardImpl<Fireblast> {
    
    private static final FilterPermanent filter = new FilterPermanent("Mountain");
    
    static {
        filter.add(new CardTypePredicate(CardType.LAND));
        filter.add(new SubtypePredicate("Mountain"));
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public Fireblast(UUID ownerId) {
        super(ownerId, 79, "Fireblast", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{4}{R}{R}");
        this.expansionSetCode = "VIS";

        this.color.setRed(true);

        // You may sacrifice two Mountains rather than pay Fireblast's mana cost.
        this.getSpellAbility().addAlternativeCost(new FireblastAlternativeCost());
        
        // Fireblast deals 4 damage to target creature or player.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetCreatureOrPlayer());
        
    }

    public Fireblast(final Fireblast card) {
        super(card);
    }

    @Override
    public Fireblast copy() {
        return new Fireblast(this);
    }
}

class FireblastAlternativeCost extends AlternativeCostImpl<FireblastAlternativeCost> {
    
    private static final FilterPermanent filter = new FilterPermanent("Mountain");
    
    static {
        filter.add(new CardTypePredicate(CardType.LAND));
        filter.add(new SubtypePredicate("Mountain"));
        filter.add(new ControllerPredicate(TargetController.YOU));
    }
    public FireblastAlternativeCost() {
        super("You may sacrifice two Mountains rather than pay Fireblast's mana cost");
        this.add(new SacrificeTargetCost(new TargetControlledPermanent(2, 2, filter, true)));
    }

    public FireblastAlternativeCost(final FireblastAlternativeCost cost) {
        super(cost);
    }

    @Override
    public FireblastAlternativeCost copy() {
        return new FireblastAlternativeCost(this);
    }

    @Override
    public boolean isAvailable(Game game, Ability source) {
        return true;
    }

    @Override
    public String getText() {
        return "You may sacrifice two Mountains rather than pay Fireblast's mana cost";
    }
}