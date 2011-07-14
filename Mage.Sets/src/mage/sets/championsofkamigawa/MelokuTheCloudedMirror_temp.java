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

package mage.sets.championsofkamigawa;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ReturnToHandTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.filter.Filter;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.Token;
import mage.target.common.TargetControlledPermanent;

/**
 * @author Loki
 */
public class MelokuTheCloudedMirror extends CardImpl<MelokuTheCloudedMirror> {

    private final static FilterControlledPermanent filter = new FilterControlledPermanent("a land");

    static {
        filter.getCardType().add(CardType.LAND);
        filter.setScopeCardType(Filter.ComparisonScope.Any);
    }

    public MelokuTheCloudedMirror(UUID ownerId) {
        super(ownerId, 74, "Meloku the Clouded Mirror", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{U}");
        this.expansionSetCode = "CHK";
        this.supertype.add("Legendary");
        this.subtype.add("Moonfolk");
        this.subtype.add("Wizard");
        this.color.setBlue(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);
        this.addAbility(FlyingAbility.getInstance());
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new CreateTokenEffect(new MelokutheCloudedMirrorToken(), 1), new GenericManaCost(1));
        ability.addCost(new ReturnToHandTargetCost(new TargetControlledPermanent(filter)));
        this.addAbility(ability);
    }

    public MelokuTheCloudedMirror(final MelokuTheCloudedMirror card) {
        super(card);
    }

    @Override
    public MelokuTheCloudedMirror copy() {
        return new MelokuTheCloudedMirror(this);
    }

}

class MelokutheCloudedMirrorToken extends Token {
    MelokutheCloudedMirrorToken() {
        super("", "a 1/1 blue Illusion creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add("Illusion");
        power = new MageInt(1);
        toughness = new MageInt(1);
        addAbility(FlyingAbility.getInstance());
    }
}