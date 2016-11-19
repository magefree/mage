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
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Pete Rossi
 */
public class SanguinePraetor extends CardImpl {

    public SanguinePraetor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{B}{B}");

        this.subtype.add("Avatar");
        this.subtype.add("Praetor");
        this.power = new MageInt(7);
        this.toughness = new MageInt(5);

        // {B}, Sacrifice a creature: Destroy each creature with the same converted mana cost as the sacrificed creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SanguinePraetorEffect(), new ManaCostsImpl("{B}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(new FilterControlledCreaturePermanent())));
        this.addAbility(ability);

    }

    public SanguinePraetor(final SanguinePraetor card) {
        super(card);
    }

    @Override
    public SanguinePraetor copy() {
        return new SanguinePraetor(this);
    }
}

class SanguinePraetorEffect extends OneShotEffect {

    public SanguinePraetorEffect() {
        super(Outcome.Damage);
        staticText = "Destroy each creature with the same converted mana cost as the sacrificed creature.";
    }

    public SanguinePraetorEffect(final SanguinePraetorEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int cmc = 0;
        for (Cost cost : source.getCosts()) {
            if (cost instanceof SacrificeTargetCost && ((SacrificeTargetCost) cost).getPermanents().size() > 0) {
                cmc = ((SacrificeTargetCost) cost).getPermanents().get(0).getConvertedManaCost();
                break;
            }
        }

        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(new FilterCreaturePermanent(), source.getControllerId(), game)) {
            if (permanent.getConvertedManaCost() == cmc) {
                permanent.destroy(source.getSourceId(), game, false);
            }
        }
        return true;
    }

    @Override
    public SanguinePraetorEffect copy() {
        return new SanguinePraetorEffect(this);
    }
}
