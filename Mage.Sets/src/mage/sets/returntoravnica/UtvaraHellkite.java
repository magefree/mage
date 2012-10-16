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
package mage.sets.returntoravnica;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.AttacksCreatureYourControlTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.permanent.token.Token;

/**
 *
 * @author LevelX2
 */
public class UtvaraHellkite extends CardImpl<UtvaraHellkite> {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("Dragon you control");
    static {
        filter.add(new SubtypePredicate("Dragon"));
    }

    public UtvaraHellkite(UUID ownerId) {
        super(ownerId, 110, "Utvara Hellkite", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{6}{R}{R}");
        this.expansionSetCode = "RTR";
        this.subtype.add("Dragon");

        this.color.setRed(true);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever a Dragon you control attacks, put a 6/6 red Dragon creature token with flying onto the battlefield.
        this.addAbility(new AttacksCreatureYourControlTriggeredAbility(new CreateTokenEffect(new UtvaraHellkiteDragonToken()),false, filter));
    }

    public UtvaraHellkite(final UtvaraHellkite card) {
        super(card);
    }

    @Override
    public UtvaraHellkite copy() {
        return new UtvaraHellkite(this);
    }
    public class UtvaraHellkiteDragonToken extends Token {

        private UtvaraHellkiteDragonToken() {
            super("Dragon", "6/6 red Dragon creature token with flying");
            cardType.add(CardType.CREATURE);
            color = ObjectColor.RED;
            subtype.add("Dragon");
            power = new MageInt(6);
            toughness = new MageInt(6);
            addAbility(FlyingAbility.getInstance());
        }
    }
}
