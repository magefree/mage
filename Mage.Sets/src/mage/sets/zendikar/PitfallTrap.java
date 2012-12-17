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
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.costs.AlternativeCostImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.target.common.TargetAttackingCreature;

/**
 *
 * @author jeffwadsworth
 */
public class PitfallTrap extends CardImpl<PitfallTrap> {

    private static final FilterAttackingCreature filter = new FilterAttackingCreature("creature without flying");

    static {
        filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public PitfallTrap(UUID ownerId) {
        super(ownerId, 32, "Pitfall Trap", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{2}{W}");
        this.expansionSetCode = "ZEN";
        this.subtype.add("Trap");

        this.color.setWhite(true);

        // If exactly one creature is attacking, you may pay {W} rather than pay Pitfall Trap's mana cost.
        this.getSpellAbility().addAlternativeCost(new PitfallTrapAlternativeCost());
        
        // Destroy target attacking creature without flying.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetAttackingCreature(1, 1, filter, false));
    }

    public PitfallTrap(final PitfallTrap card) {
        super(card);
    }

    @Override
    public PitfallTrap copy() {
        return new PitfallTrap(this);
    }
}

class PitfallTrapAlternativeCost extends AlternativeCostImpl<PitfallTrapAlternativeCost> {

    public PitfallTrapAlternativeCost() {
        super("you may pay {W} rather than pay Pitfall Trap's mana cost");
        this.add(new ManaCostsImpl("{W}"));
    }

    public PitfallTrapAlternativeCost(final PitfallTrapAlternativeCost cost) {
        super(cost);
    }

    @Override
    public PitfallTrapAlternativeCost copy() {
        return new PitfallTrapAlternativeCost(this);
    }

    @Override
    public boolean isAvailable(Game game, Ability source) {
        if (game.getCombat().getAttackers().size() == 1) {
            return true;
        }
        return false;
    }

    @Override
    public String getText() {
        return "If exactly one creature is attacking, you may pay {W} rather than pay Pitfall Trap's mana cost";
    }
}