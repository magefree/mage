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
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author fireshoes
 */
public class DemonOfDarkSchemes extends CardImpl {

    public DemonOfDarkSchemes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{B}{B}");
        this.subtype.add("Demon");
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Demon of Dark Schemes enters the battlefield, all other creatures get -2/-2 until end of turn.
        Effect effect = new BoostAllEffect(-2, -2, Duration.EndOfTurn, true);
        effect.setText("all other creatures get -2/-2 until end of turn");
        this.addAbility(new EntersBattlefieldTriggeredAbility(effect, false));

        // Whenever another creature dies, you get {E}.
        this.addAbility(new DiesCreatureTriggeredAbility(new GetEnergyCountersControllerEffect(1), false, true));

        // {2}{B}, Pay {E}{E}{E}{E}: Put target creature card from a graveyard onto the battlefield under your control tapped.
        effect = new ReturnFromGraveyardToBattlefieldTargetEffect(true);
        effect.setText("Put target creature card from a graveyard onto the battlefield under your control tapped");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl("{2}{B}"));
        ability.addCost(new PayEnergyCost(4));
        ability.addTarget(new TargetCardInGraveyard(new FilterCreatureCard("creature card from a graveyard")));
        this.addAbility(ability);
    }

    public DemonOfDarkSchemes(final DemonOfDarkSchemes card) {
        super(card);
    }

    @Override
    public DemonOfDarkSchemes copy() {
        return new DemonOfDarkSchemes(this);
    }
}
