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
package mage.cards.s;

import java.util.UUID;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.permanent.token.Token;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public class SkarrgGuildmage extends CardImpl {

    public SkarrgGuildmage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}{G}");
        this.subtype.add("Human");
        this.subtype.add("Shaman");

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {R}{G}: Creatures you control gain trample until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                Zone.BATTLEFIELD, new GainAbilityAllEffect(TrampleAbility.getInstance(), Duration.EndOfTurn, new FilterControlledCreaturePermanent(), "Creatures you control gain trample until end of turn"),
                new ManaCostsImpl("{R}{G}")));
        // {1}{R}{G}: Target land you control becomes a 4/4 Elemental creature until end of turn. It's still a land.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesCreatureTargetEffect(new SkarrgGuildmageToken(), false, true, Duration.EndOfTurn), new ManaCostsImpl("{1}{R}{G}") );
        ability.addTarget(new TargetPermanent(new FilterControlledLandPermanent()));
        this.addAbility(ability);

    }

    public SkarrgGuildmage(final SkarrgGuildmage card) {
        super(card);
    }

    @Override
    public SkarrgGuildmage copy() {
        return new SkarrgGuildmage(this);
    }
}

class SkarrgGuildmageToken extends Token {

    public SkarrgGuildmageToken() {
        super("", "4/4 Elemental creature");
        this.cardType.add(CardType.CREATURE);

        this.subtype.add("Elemental");
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
    }
}
