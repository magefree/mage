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
package mage.cards.m;

import java.util.UUID;
import mage.ConditionalMana;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.BasicManaEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.mana.BasicManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;

/**
 * @author nantuko
 */
public class MyrReservoir extends CardImpl {

    private static final FilterCard myrCardFilter = new FilterCard("Myr card from your graveyard");

    static {
        myrCardFilter.add(new SubtypePredicate("Myr"));
    }

    public MyrReservoir(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // {tap}: Add {C}{C} to your mana pool. Spend this mana only to cast Myr spells or activate abilities of Myr.
        this.addAbility(new MyrReservoirManaAbility());

        // {3}, {tap}: Return target Myr card from your graveyard to your hand.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnToHandTargetEffect(), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCardInYourGraveyard(myrCardFilter));
        this.addAbility(ability);
    }

    public MyrReservoir(final MyrReservoir card) {
        super(card);
    }

    @Override
    public MyrReservoir copy() {
        return new MyrReservoir(this);
    }
}

class MyrReservoirManaAbility extends BasicManaAbility {

    MyrReservoirManaAbility() {
        super(new BasicManaEffect(new MyrConditionalMana()));
        this.netMana.add(Mana.ColorlessMana(2));
    }

    MyrReservoirManaAbility(MyrReservoirManaAbility ability) {
        super(ability);
    }

    @Override
    public MyrReservoirManaAbility copy() {
        return new MyrReservoirManaAbility(this);
    }
}

class MyrConditionalMana extends ConditionalMana {

    public MyrConditionalMana() {
        super(Mana.ColorlessMana(2));
        staticText = "Spend this mana only to cast Myr spells or activate abilities of Myr";
        addCondition(new MyrManaCondition());
    }
}

class MyrManaCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject object = game.getObject(source.getSourceId());
        if (object != null && object.hasSubtype("Myr", game)) {
            return true;
        }
        return false;
    }
}
