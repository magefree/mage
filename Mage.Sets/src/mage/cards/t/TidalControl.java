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
package mage.cards.t;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.costs.OrCost;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.TargetSpell;

/**
 *
 * @author L_J
 */
public class TidalControl extends CardImpl {
    private static final FilterSpell filter = new FilterSpell("red or green spell");
    static{
        filter.add(Predicates.or(new ColorPredicate(ObjectColor.RED), new ColorPredicate(ObjectColor.GREEN)));
    }

    public TidalControl(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}{U}");

        // Cumulative upkeep-Pay {2}.
        this.addAbility(new CumulativeUpkeepAbility(new ManaCostsImpl("{2}")));

        // Pay 2 life or {2}: Counter target red or green spell. Any player may activate this ability.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CounterTargetEffect(), new OrCost(new PayLifeCost(2), new ManaCostsImpl("{2}"), "pay 2 life or pay {2}"));
        ability.addTarget(new TargetSpell(filter));
        ability.setMayActivate(TargetController.ANY);
        ability.addEffect(new InfoEffect("Any player may activate this ability"));
        this.addAbility(ability);
    }

    public TidalControl(final TidalControl card) {
        super(card);
    }

    @Override
    public TidalControl copy() {
        return new TidalControl(this);
    }
}
