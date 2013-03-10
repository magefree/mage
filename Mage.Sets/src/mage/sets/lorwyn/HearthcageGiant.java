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
package mage.sets.lorwyn;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continious.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.permanent.token.Token;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author Loki
 */
public class HearthcageGiant extends CardImpl<HearthcageGiant> {

    private static final FilterControlledPermanent filterElemental = new FilterControlledPermanent("Elemental");
    private static final FilterCreaturePermanent filterGiant = new FilterCreaturePermanent("Giant");

    static {
        filterElemental.add(new SubtypePredicate("Elemental"));
        filterGiant.add(new SubtypePredicate("Giant"));
    }

    public HearthcageGiant(UUID ownerId) {
        super(ownerId, 174, "Hearthcage Giant", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{6}{R}{R}");
        this.expansionSetCode = "LRW";
        this.subtype.add("Giant");
        this.subtype.add("Warrior");
        this.color.setRed(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new RedElementalToken(), 2), false));
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new BoostTargetEffect(3, 1, Constants.Duration.EndOfTurn), new SacrificeTargetCost(new TargetControlledPermanent(filterElemental)));
        ability.addTarget(new TargetCreaturePermanent(filterGiant));
        this.addAbility(ability);
    }

    public HearthcageGiant(final HearthcageGiant card) {
        super(card);
    }

    @Override
    public HearthcageGiant copy() {
        return new HearthcageGiant(this);
    }
}

class RedElementalToken extends Token {
    RedElementalToken() {
        super("Elemental Shaman", "3/1 red Elemental Shaman creature token");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add("Elemental");
        subtype.add("Shaman");
        power = new MageInt(3);
        toughness = new MageInt(1);
    }
}