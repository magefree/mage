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
package mage.sets.commander2013;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.dynamicvalue.common.ManaSpentToCastCount;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continious.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.permanent.token.Token;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class ProsshSkyraiderOfKher extends CardImpl<ProsshSkyraiderOfKher> {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("another creature");
    static {
        filter.add(new AnotherPredicate());
    }

    public ProsshSkyraiderOfKher(UUID ownerId) {
        super(ownerId, 204, "Prossh, Skyraider of Kher", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{3}{B}{R}{G}");
        this.expansionSetCode = "C13";
        this.supertype.add("Legendary");
        this.subtype.add("Dragon");

        this.color.setRed(true);
        this.color.setGreen(true);
        this.color.setBlack(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When you cast Prossh, Skyraider of Kher, put X 0/1 red Kobold creature tokens named Kobolds of Kher Keep onto the battlefield, where X is the amount of mana spent to cast Prossh.
        this.addAbility(new CastSourceTriggeredAbility(new CreateTokenEffect(new ProsshKoboldToken(), new ManaSpentToCastCount()), false));
        // Sacrifice another creature: Prossh gets +1/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1,0, Duration.EndOfTurn), new SacrificeTargetCost(new TargetControlledCreaturePermanent(1,1,filter, true))));
    }

    public ProsshSkyraiderOfKher(final ProsshSkyraiderOfKher card) {
        super(card);
    }

    @Override
    public ProsshSkyraiderOfKher copy() {
        return new ProsshSkyraiderOfKher(this);
    }
}

class ProsshKoboldToken extends Token {

    public ProsshKoboldToken() {
        super("Kobolds of Kher Keep", "0/1 red Kobold creature tokens");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add("Kobold");
        power = new MageInt(0);
        toughness = new MageInt(1);
    }
}
