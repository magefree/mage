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
package mage.sets.futuresight;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.BasicManaEffect;
import mage.abilities.mana.ActivateIfConditionManaAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;

/**
 *
 * @author dustinconrad
 */
public class NimbusMaze extends CardImpl {

    private static final FilterControlledPermanent controlIsland = new FilterControlledPermanent("you control an Island");
    private static final FilterControlledPermanent controlPlains = new FilterControlledPermanent("you control a Plains");

    static {
        controlIsland.add(new SubtypePredicate("Island"));
        controlPlains.add(new SubtypePredicate("Plains"));
    }

    public NimbusMaze(UUID ownerId) {
        super(ownerId, 178, "Nimbus Maze", Rarity.RARE, new CardType[]{CardType.LAND}, "");
        this.expansionSetCode = "FUT";

        // {tap}: Add {1} to your mana pool.
        this.addAbility(new ColorlessManaAbility());
        // {tap}: Add {W} to your mana pool. Activate this ability only if you control an Island.
        this.addAbility(new ActivateIfConditionManaAbility(
                Zone.BATTLEFIELD,
                new BasicManaEffect(Mana.WhiteMana),
                new TapSourceCost(),
                new PermanentsOnTheBattlefieldCondition(controlIsland)));
        // {tap}: Add {U} to your mana pool. Activate this ability only if you control a Plains.
        this.addAbility(new ActivateIfConditionManaAbility(
                Zone.BATTLEFIELD,
                new BasicManaEffect(Mana.BlueMana),
                new TapSourceCost(),
                new PermanentsOnTheBattlefieldCondition(controlPlains)));
    }

    public NimbusMaze(final NimbusMaze card) {
        super(card);
    }

    @Override
    public NimbusMaze copy() {
        return new NimbusMaze(this);
    }
}

class FilterPermanentCost extends CostImpl {

    private final FilterPermanent filter;

    public FilterPermanentCost(FilterPermanent filter) {
        this.filter = filter;
        this.text = "Activate this ability only if " + filter.getMessage();
    }

    public FilterPermanentCost(final FilterPermanentCost cost) {
        super(cost);
        this.filter = cost.filter;
    }

    @Override
    public FilterPermanentCost copy() {
        return new FilterPermanentCost(this);
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        return game.getBattlefield().contains(filter, controllerId, 1, game);
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana) {
        this.paid = true;
        return paid;
    }
}
