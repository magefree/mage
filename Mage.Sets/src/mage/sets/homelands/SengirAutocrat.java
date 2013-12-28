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
package mage.sets.homelands;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileAllEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.Token;

/**
 *
 * @author Quercitron
 */
public class SengirAutocrat extends CardImpl<SengirAutocrat> {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Serf tokens");

    static {
        filter.add(new SubtypePredicate("Serf"));
        filter.add(new TokenPredicate());
    }
    
    public SengirAutocrat(UUID ownerId) {
        super(ownerId, 19, "Sengir Autocrat", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.expansionSetCode = "HML";
        this.subtype.add("Human");

        this.color.setBlack(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Sengir Autocrat enters the battlefield, put three 0/1 black Serf creature tokens onto the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new SerfToken(), 3));
        this.addAbility(ability);
        // When Sengir Autocrat leaves the battlefield, exile all Serf tokens.
        ability = new LeavesBattlefieldTriggeredAbility(new ExileAllEffect(filter), false);
        this.addAbility(ability);
    }

    public SengirAutocrat(final SengirAutocrat card) {
        super(card);
    }

    @Override
    public SengirAutocrat copy() {
        return new SengirAutocrat(this);
    }
}

class SerfToken extends Token {
    public SerfToken() {
        super("Serf", "0/1 black Serf creature token");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add("Serf");
        power = new MageInt(0);
        toughness = new MageInt(1);
    }
}