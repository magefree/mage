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
package mage.sets.eventide;

import java.util.UUID;

import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardControllerEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 * @author Loki
 */
public class Scarecrone extends CardImpl<Scarecrone> {

    private static final FilterCard filter = new FilterCard("artifact creature card from your graveyard");
    private static final FilterControlledCreaturePermanent filterScarecrow = new FilterControlledCreaturePermanent("Scarecrow");
    static {
        filter.add(new CardTypePredicate(CardType.ARTIFACT));
        filter.add(new CardTypePredicate(CardType.CREATURE));
        filter.add(new SubtypePredicate("Scarecrow"));
    }

    public Scarecrone(UUID ownerId) {
        super(ownerId, 172, "Scarecrone", Rarity.RARE, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");
        this.expansionSetCode = "EVE";
        this.subtype.add("Scarecrow");
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {1}, Sacrifice a Scarecrow: Draw a card.
        Ability firstAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardControllerEffect(1), new GenericManaCost(1));
        firstAbility.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(1,1, filterScarecrow, false, true)));
        this.addAbility(firstAbility);

        // {4}, {T}: Return target artifact creature card from your graveyard to the battlefield.
        Ability secondAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnFromGraveyardToBattlefieldTargetEffect(), new GenericManaCost(4));
        secondAbility.addCost(new TapSourceCost());
        secondAbility.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(secondAbility);
    }

    public Scarecrone(final Scarecrone card) {
        super(card);
    }

    @Override
    public Scarecrone copy() {
        return new Scarecrone(this);
    }
}
