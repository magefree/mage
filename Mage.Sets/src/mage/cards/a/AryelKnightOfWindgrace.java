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
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.VariableCostImpl;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetAdjustment;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.token.KnightToken;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author jack-the-BOSS
 */
public class AryelKnightOfWindgrace extends CardImpl {

    public AryelKnightOfWindgrace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // {2}{W}, {T}: Create a 2/2 white Knight creature token with vigilance.
        Ability tokenAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new KnightToken()), new ManaCostsImpl("{2}{W}"));
        tokenAbility.addCost(new TapSourceCost());
        this.addAbility(tokenAbility);

        // {B}, {T}, Tap X untapped Knights you control: Destroy target creature with power X or less.
        //Simple costs
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect()
                .setText("Destroy target creature with power X or less"), new ManaCostsImpl("{B}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new AryelTapXTargetCost());
        ability.setTargetAdjustment(TargetAdjustment.CREATURE_POWER_X_OR_LESS);
        this.addAbility(ability);
        ability.getOriginalId();
    }

    public AryelKnightOfWindgrace(final AryelKnightOfWindgrace card) {
        super(card);
    }

    @Override
    public AryelKnightOfWindgrace copy() {
        return new AryelKnightOfWindgrace(this);
    }
}

class AryelTapXTargetCost extends VariableCostImpl {

    static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("untapped Knights you control");

    static {
        filter.add(Predicates.not(new TappedPredicate()));
        filter.add(new SubtypePredicate(SubType.KNIGHT));
    }

    public AryelTapXTargetCost() {
        super("controlled untapped Knights you would like to tap");
        this.text = "Tap X untapped Knights you control";
    }

    public AryelTapXTargetCost(final AryelTapXTargetCost cost) {
        super(cost);
    }

    @Override
    public AryelTapXTargetCost copy() {
        return new AryelTapXTargetCost(this);
    }

    @Override
    public int getMaxValue(Ability source, Game game) {
        return game.getBattlefield().count(filter, source.getSourceId(), source.getControllerId(), game);
    }

    @Override
    public Cost getFixedCostsFromAnnouncedValue(int xValue) {
        TargetControlledPermanent target = new TargetControlledPermanent(xValue, xValue, filter, true);
        return new TapTargetCost(target);
    }
}
