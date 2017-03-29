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
package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.permanent.token.EldraziHorrorToken;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class ExtricatorOfFlesh extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("non-Eldrazi creature");

    static {
        filter.add(Predicates.not(new SubtypePredicate("Eldrazi")));
    }

    public ExtricatorOfFlesh(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"");
        this.subtype.add("Eldrazi");
        this.subtype.add("Horror");
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // this card is the second face of double-faced card
        this.nightCard = true;

        // Eldrazi you control have vigilance
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledEffect(
                VigilanceAbility.getInstance(), Duration.WhileOnBattlefield, new FilterControlledCreaturePermanent("Eldrazi", "Eldrazi you control "))));

        // {2}, {T}, Sacrifice a non-Eldrazi creature: Create a 3/2 colorless Eldrazi Horror creature token.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new EldraziHorrorToken()), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(1, 1, filter, true)));
        this.addAbility(ability);
    }

    public ExtricatorOfFlesh(final ExtricatorOfFlesh card) {
        super(card);
    }

    @Override
    public ExtricatorOfFlesh copy() {
        return new ExtricatorOfFlesh(this);
    }
}
