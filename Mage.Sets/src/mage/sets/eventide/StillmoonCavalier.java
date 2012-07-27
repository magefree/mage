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
package mage.sets.eventide;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continious.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.ColorlessPredicate;

/**
 *
 * @author Loki
 */
public class StillmoonCavalier extends CardImpl<StillmoonCavalier> {
    private static final FilterCard filterBlack = new FilterCard("black");
    private static final FilterCard filterWhite = new FilterCard("white");

    static {
        filterBlack.add(new ColorPredicate(ObjectColor.BLACK));
        filterWhite.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public StillmoonCavalier(UUID ownerId) {
        super(ownerId, 95, "Stillmoon Cavalier", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{W/B}{W/B}");
        this.expansionSetCode = "EVE";
        this.subtype.add("Zombie");
        this.subtype.add("Knight");

        this.color.setBlack(true);
        this.color.setWhite(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Protection from white and from black
        this.addAbility(new ProtectionAbility(filterBlack));
        this.addAbility(new ProtectionAbility(filterWhite));
        // {WB}: Stillmoon Cavalier gains flying until end of turn.
        this.addAbility(new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new GainAbilitySourceEffect(FlyingAbility.getInstance(), Constants.Duration.EndOfTurn), new ManaCostsImpl("{W/B}")));
        // {WB}: Stillmoon Cavalier gains first strike until end of turn.
        this.addAbility(new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new GainAbilitySourceEffect(FirstStrikeAbility.getInstance(), Constants.Duration.EndOfTurn), new ManaCostsImpl("{W/B}")));
        // {WB}{WB}: Stillmoon Cavalier gets +1/+0 until end of turn.
    }

    public StillmoonCavalier(final StillmoonCavalier card) {
        super(card);
    }

    @Override
    public StillmoonCavalier copy() {
        return new StillmoonCavalier(this);
    }
}
