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

package mage.sets.darksteel;

import java.util.UUID;

import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.Token;
import mage.target.common.TargetControlledPermanent;

/**
 * @author Loki
 */
public class WandOfTheElements extends CardImpl<WandOfTheElements> {

    private static final FilterControlledPermanent islandFilter = new FilterControlledPermanent("an Island");
    private static final FilterControlledPermanent mountainFilter = new FilterControlledPermanent("a Mountain");

    static {
        islandFilter.getName().add("Island");
        mountainFilter.getName().add("Mountain");
    }

    public WandOfTheElements(UUID ownerId) {
        super(ownerId, 158, "Wand of the Elements", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{4}");
        this.expansionSetCode = "DST";
        Ability firstAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new WandOfTheElementsFirstToken()), new TapSourceCost());
        firstAbility.addCost(new SacrificeTargetCost(new TargetControlledPermanent(islandFilter)));
        this.addAbility(firstAbility);
        Ability secondAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new WandOfTheElementsSecondToken()), new TapSourceCost());
        secondAbility.addCost(new SacrificeTargetCost(new TargetControlledPermanent(mountainFilter)));
        this.addAbility(secondAbility);

    }

    public WandOfTheElements(final WandOfTheElements card) {
        super(card);
    }

    @Override
    public WandOfTheElements copy() {
        return new WandOfTheElements(this);
    }
}

class WandOfTheElementsFirstToken extends Token {
    public WandOfTheElementsFirstToken() {
        super("", "2/2 blue Elemental creature token with flying");
        cardType.add(CardType.CREATURE);
        this.subtype.add("Elemental");
        this.color.setBlue(true);
        power = new MageInt(2);
        toughness = new MageInt(2);
        this.addAbility(FlyingAbility.getInstance());
    }
}


class WandOfTheElementsSecondToken extends Token {
    public WandOfTheElementsSecondToken() {
        super("", "3/3 red Elemental creature token");
        cardType.add(CardType.CREATURE);
        this.subtype.add("Elemental");
        this.color.setRed(true);
        power = new MageInt(3);
        toughness = new MageInt(3);
    }
}

