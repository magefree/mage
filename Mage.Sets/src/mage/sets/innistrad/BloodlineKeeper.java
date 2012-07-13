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
package mage.sets.innistrad;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.token.Token;

/**
 *
 * @author Loki
 */
public class BloodlineKeeper extends CardImpl<BloodlineKeeper> {

    public BloodlineKeeper(UUID ownerId) {
        super(ownerId, 90, "Bloodline Keeper", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");
        this.expansionSetCode = "ISD";
        this.subtype.add("Vampire");

        this.color.setBlack(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.canTransform = true;
        this.secondSideCard = new LordOfLineage(ownerId);

        this.addAbility(FlyingAbility.getInstance());
        // {tap}: Put a 2/2 black Vampire creature token with flying onto the battlefield.
        this.addAbility(new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new CreateTokenEffect(new VampireToken()), new TapSourceCost()));
        // {B}: Transform Bloodline Keeper. Activate this ability only if you control five or more Vampires.
        this.addAbility(new TransformAbility());
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new TransformSourceEffect(true), new ColoredManaCost(Constants.ColoredManaSymbol.B));
        ability.addCost(new ControlFiveVampiresCost());
        this.addAbility(ability);
    }

    public BloodlineKeeper(final BloodlineKeeper card) {
        super(card);
    }

    @Override
    public BloodlineKeeper copy() {
        return new BloodlineKeeper(this);
    }
}

class VampireToken extends Token {
    VampireToken() {
        super("Vampire", "a 2/2 black Vampire creature token with flying");
        cardType.add(Constants.CardType.CREATURE);
        color.setBlack(true);
        subtype.add("Vampire");
        power = new MageInt(2);
        toughness = new MageInt(2);
        addAbility(FlyingAbility.getInstance());
    }
}

class ControlFiveVampiresCost extends CostImpl<ControlFiveVampiresCost> {
    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(new SubtypePredicate("Vampire"));
    }

    public ControlFiveVampiresCost() {
        this.text = "Activate this ability only if you control five or more Vampires";
    }

    public ControlFiveVampiresCost(final ControlFiveVampiresCost cost) {
        super(cost);
    }

    @Override
    public boolean canPay(UUID sourceId, UUID controllerId, Game game) {
        return game.getBattlefield().contains(filter, controllerId, 5, game);
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana) {
        this.paid = true;
        return paid;
    }

    @Override
    public ControlFiveVampiresCost copy() {
        return new ControlFiveVampiresCost(this);
    }
}