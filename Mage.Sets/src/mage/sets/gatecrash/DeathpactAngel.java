/*
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
package mage.sets.gatecrash;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.permanent.token.Token;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author Plopman
 */
public class DeathpactAngel extends CardImpl<DeathpactAngel> {

    public DeathpactAngel(UUID ownerId) {
        super(ownerId, 153, "Deathpact Angel", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{3}{W}{B}{B}");
        this.expansionSetCode = "GTC";
        this.subtype.add("Angel");

        this.color.setBlack(true);
        this.color.setWhite(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        //Flying
        this.addAbility(FlyingAbility.getInstance());
        //When Deathpact Angel dies, put a 1/1 white and black Cleric creature token onto the battlefield. It has "{3}{W}{B}{B}, {T}, Sacrifice this creature: Return a card named Deathpact Angel from your graveyard to the battlefield."
        this.addAbility(new DiesTriggeredAbility(new CreateTokenEffect(new DeathpactAngelToken())));
    }

    public DeathpactAngel(final DeathpactAngel card) {
        super(card);
    }

    @Override
    public DeathpactAngel copy() {
        return new DeathpactAngel(this);
    }
}


class DeathpactAngelToken extends Token {

    private static final FilterCreatureCard filter = new FilterCreatureCard("card named Deathpact Angel from your graveyard");
    static {
        filter.add(new NamePredicate("Deathpact Angel"));
    }
    public DeathpactAngelToken() {
        super("Cleric", "1/1 white and black Cleric creature token onto the battlefield. It has \"{3}{W}{B}{B}, {T}, Sacrifice this creature: Return a card named Deathpact Angel from your graveyard to the battlefield.\"");
        cardType.add(CardType.CREATURE);

        color.setWhite(true);
        color.setBlack(true);
        
        subtype.add("Cleric");
        
        power = new MageInt(1);
        toughness = new MageInt(1);
        
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new ReturnFromGraveyardToBattlefieldTargetEffect(), new ManaCostsImpl("{3}{W}{B}{B}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

}