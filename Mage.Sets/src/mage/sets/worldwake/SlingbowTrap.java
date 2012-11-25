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
package mage.sets.worldwake;

import java.util.List;
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
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetAttackingCreature;

/**
 *
 * @author jeffwadsworth
 */
public class SlingbowTrap extends CardImpl<SlingbowTrap> {

    private static final FilterAttackingCreature filter = new FilterAttackingCreature("attacking creature with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public SlingbowTrap(UUID ownerId) {
        super(ownerId, 111, "Slingbow Trap", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{3}{G}");
        this.expansionSetCode = "WWK";
        this.subtype.add("Trap");

        this.color.setGreen(true);

        // If a black creature with flying is attacking, you may pay {G} rather than pay Slingbow Trap's mana cost.
        this.getSpellAbility().addAlternativeCost(new SlingbowTrapAlternativeCost());

        // Destroy target attacking creature with flying.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetAttackingCreature(1, 1, filter, false));
    }

    public SlingbowTrap(final SlingbowTrap card) {
        super(card);
    }

    @Override
    public SlingbowTrap copy() {
        return new SlingbowTrap(this);
    }
}

class SlingbowTrapAlternativeCost extends AlternativeCostImpl<SlingbowTrapAlternativeCost> {

    public SlingbowTrapAlternativeCost() {
        super("you may pay {G} rather than pay {this}'s mana cost");
        this.add(new ManaCostsImpl("{G}"));
    }

    public SlingbowTrapAlternativeCost(final SlingbowTrapAlternativeCost cost) {
        super(cost);
    }

    @Override
    public SlingbowTrapAlternativeCost copy() {
        return new SlingbowTrapAlternativeCost(this);
    }

    @Override
    public boolean isAvailable(Game game, Ability source) {
        List<UUID> attackers = game.getCombat().getAttackers();
        for (UUID creatureId : attackers) {
            Permanent creature = game.getPermanent(creatureId);
            if (creature.getColor().isBlack()
                    && creature.getAbilities().contains(FlyingAbility.getInstance())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getText() {
        return "If a black creature with flying is attacking, you may pay {G} rather than pay Slingbow Trap's mana cost";
    }
}
