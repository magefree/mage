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
package mage.sets.onslaught;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.permanent.token.Token;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class VoiceOfTheWoods extends CardImpl<VoiceOfTheWoods> {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("untapped Elves you control");

    static {
        filter.add(Predicates.not(new TappedPredicate()));
        filter.add(new SubtypePredicate("Elf"));
    }

    public VoiceOfTheWoods(UUID ownerId) {
        super(ownerId, 297, "Voice of the Woods", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");
        this.expansionSetCode = "ONS";
        this.subtype.add("Elf");

        this.color.setGreen(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Tap five untapped Elves you control: Put a 7/7 green Elemental creature token with trample onto the battlefield.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new CreateTokenEffect(new VoiceOfTheWoodsElementalToken()),
                new TapTargetCost(new TargetControlledCreaturePermanent(5,5, filter, false)));
        this.addAbility(ability);
    }

    public VoiceOfTheWoods(final VoiceOfTheWoods card) {
        super(card);
    }

    @Override
    public VoiceOfTheWoods copy() {
        return new VoiceOfTheWoods(this);
    }
}

class VoiceOfTheWoodsElementalToken extends Token {

    VoiceOfTheWoodsElementalToken() {
        super("Elemental", "7/7 green Elemental creature token with trample");
        this.setOriginalExpansionSetCode("EVG");
        cardType.add(CardType.CREATURE);
        subtype.add("Elemental");

        color.setGreen(true);
        power = new MageInt(7);
        toughness = new MageInt(7);

        addAbility(TrampleAbility.getInstance());
    }
}