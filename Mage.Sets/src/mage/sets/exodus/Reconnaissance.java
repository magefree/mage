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
package mage.sets.exodus;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.filter.predicate.permanent.AttackingPredicate;

/**
 *
 * @author spjspj
 */
public class Reconnaissance extends CardImpl {

    private static FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("attacking creature controlled by you");

    static {
        filter.add(new AttackingPredicate());
    }

    public Reconnaissance(UUID ownerId) {
        super(ownerId, 17, "Reconnaissance", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{W}");
        this.expansionSetCode = "EXO";

        // {0}: Remove target attacking creature you control from combat and untap it.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReconnaissanceRemoveFromCombatEffect(), new ManaCostsImpl("{0}"));
        ability.addTarget(new TargetControlledCreaturePermanent(filter));
        this.addAbility(ability);
    }

    public Reconnaissance(final Reconnaissance card) {
        super(card);
    }

    @Override
    public Reconnaissance copy() {
        return new Reconnaissance(this);
    }
}

class ReconnaissanceRemoveFromCombatEffect extends OneShotEffect {

    public ReconnaissanceRemoveFromCombatEffect() {
        super(Outcome.Benefit);
        this.staticText = "Remove target attacking creature from combat and untap it";
    }

    public ReconnaissanceRemoveFromCombatEffect(final ReconnaissanceRemoveFromCombatEffect effect) {
        super(effect);
    }

    @Override
    public ReconnaissanceRemoveFromCombatEffect copy() {
        return new ReconnaissanceRemoveFromCombatEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(source.getFirstTarget());
        Player player = game.getPlayer(source.getControllerId());

        if (creature != null && player != null && creature.isAttacking()) {
            creature.removeFromCombat(game);
            creature.untap(game);
            return true;
        }
        return false;
    }
}
