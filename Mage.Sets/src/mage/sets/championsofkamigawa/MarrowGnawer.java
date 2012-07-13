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
import mage.Constants.Zone;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continious.GainAbilityAllEffect;
import mage.abilities.keyword.FearAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.permanent.token.Token;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LevelX
 */
public class MarrowGnawer extends CardImpl<MarrowGnawer> {

    private static final FilterCreaturePermanent filterFear = new FilterCreaturePermanent("Rat creatures");
    private static final FilterControlledCreaturePermanent filterSacrifice = new FilterControlledCreaturePermanent("a Rat");
    private static final FilterControlledCreaturePermanent filter3 = new FilterControlledCreaturePermanent("Rats you control");

    {
        SubtypePredicate ratPredicate = new SubtypePredicate("Rat");
        filterFear.add(ratPredicate);
        filterSacrifice.add(ratPredicate);
        filter3.add(ratPredicate);
    }

    public MarrowGnawer (UUID ownerId) {
        super(ownerId, 124, "Marrow-Gnawer", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.expansionSetCode = "CHK";
        this.subtype.add("Rat");
        this.subtype.add("Rogue");
    this.color.setBlack(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Rat creatures have fear. (They can't be blocked except by artifact creatures and/or black creatures.)
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(FearAbility.getInstance(), Constants.Duration.WhileOnBattlefield, filterFear)));

        // {T}, Sacrifice a Rat: Put X 1/1 black Rat creature tokens onto the battlefield, where X is the number of Rats you control.
        Ability ability;
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new RatToken(),new PermanentsOnBattlefieldCount(filter3)), new SacrificeTargetCost(new TargetControlledPermanent(filterSacrifice)));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    public MarrowGnawer (final MarrowGnawer card) {
        super(card);
    }

    @Override
    public MarrowGnawer copy() {
        return new MarrowGnawer(this);
    }

}

class RatToken extends Token {

    public RatToken() {
        super("Rat", "1/1 black Rat creature token");
        cardType.add(CardType.CREATURE);
        color = ObjectColor.BLACK;
        subtype.add("Rat");
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

}