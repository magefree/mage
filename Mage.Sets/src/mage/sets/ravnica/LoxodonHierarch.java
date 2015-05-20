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
package mage.sets.ravnica;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.RegenerateAllEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author dustinconrad
 */
public class LoxodonHierarch extends CardImpl {

    private static FilterCreaturePermanent filter = new FilterCreaturePermanent("each creature you control");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public LoxodonHierarch(UUID ownerId) {
        super(ownerId, 214, "Loxodon Hierarch", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{G}{W}");
        this.expansionSetCode = "RAV";
        this.subtype.add("Elephant");
        this.subtype.add("Cleric");

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Loxodon Hierarch enters the battlefield, you gain 4 life.
        Ability etbAbility = new EntersBattlefieldTriggeredAbility(new GainLifeEffect(4));
        this.addAbility(etbAbility);
        // {G}{W}, Sacrifice Loxodon Hierarch: Regenerate each creature you control.
        Ability activated = new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateAllEffect(filter), new ManaCostsImpl("{G}{W}"));
        activated.addCost(new SacrificeSourceCost());
        this.addAbility(activated);
    }

    public LoxodonHierarch(final LoxodonHierarch card) {
        super(card);
    }

    @Override
    public LoxodonHierarch copy() {
        return new LoxodonHierarch(this);
    }
}
