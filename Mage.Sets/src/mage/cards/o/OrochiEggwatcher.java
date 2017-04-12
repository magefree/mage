/*
 *  
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
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
 * 
 */

package mage.cards.o;

import mage.constants.ComparisonType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.FlipSourceEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.permanent.token.SnakeToken;
import mage.game.permanent.token.Token;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX
 */
public class OrochiEggwatcher extends CardImpl {

    public OrochiEggwatcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.subtype.add("Snake");
        this.subtype.add("Shaman");

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.flipCard = true;
        this.flipCardName = "Shidako, Broodmistress";

        // {2}{G}, {T}: Create a 1/1 green Snake creature token. If you control ten or more creatures, flip Orochi Eggwatcher.
        Ability ability;
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new SnakeToken()), new ManaCostsImpl("{2}{G}"));
        ability.addCost(new TapSourceCost());
        ability.addEffect(new ConditionalOneShotEffect(new FlipSourceEffect(new ShidakoBroodmistress()),
                new PermanentsOnTheBattlefieldCondition(new FilterControlledCreaturePermanent(), ComparisonType.MORE_THAN, 9), "If you control ten or more creatures, flip {this}"));
        this.addAbility(ability);
    }

    public OrochiEggwatcher(final OrochiEggwatcher card) {
        super(card);
    }

    @Override
    public OrochiEggwatcher copy() {
        return new OrochiEggwatcher(this);
    }
}

class ShidakoBroodmistress extends Token {

    ShidakoBroodmistress() {
        super("Shidako, Broodmistress", "");
        addSuperType(SuperType.LEGENDARY);
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add("Snake");
        subtype.add("Shaman");
        power = new MageInt(3);
        toughness = new MageInt(3);
        // {G}, Sacrifice a creature: Target creature gets +3/+3 until end of turn.
        Ability ability;
        ability = new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new BoostTargetEffect(3, 3, Duration.EndOfTurn),
                new ManaCostsImpl("{G}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent()));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }
}

