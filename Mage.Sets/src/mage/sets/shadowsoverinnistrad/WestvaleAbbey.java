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
package mage.sets.shadowsoverinnistrad;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.permanent.token.Token;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author fireshoes
 */
public class WestvaleAbbey extends CardImpl {

    public WestvaleAbbey(UUID ownerId) {
        super(ownerId, 281, "Westvale Abbey", Rarity.RARE, new CardType[]{CardType.LAND}, "");
        this.expansionSetCode = "SOI";

        this.transformable = true;
        this.secondSideCard = new OrmendahlProfanePrince(ownerId);

        // {T}: Add {C} to your mana pool.
        this.addAbility(new ColorlessManaAbility());

        // {5}, {T}, Pay 1 life: Put a 1/1 white and black Human Cleric creature token onto the battlefield.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new HumanClericToken()), new GenericManaCost(5));
        ability.addCost(new TapSourceCost());
        ability.addCost(new PayLifeCost(1));
        this.addAbility(ability);

        // {5}, {T}, Sacrifice five creatures: Transform Westvale Abbey and untap it.
        this.addAbility(new TransformAbility());
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new TransformSourceEffect(true), new GenericManaCost(5));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(5, 5, new FilterControlledCreaturePermanent("five creatures"), true)));
        ability.addEffect(new UntapSourceEffect());
        this.addAbility(ability);
    }

    public WestvaleAbbey(final WestvaleAbbey card) {
        super(card);
    }

    @Override
    public WestvaleAbbey copy() {
        return new WestvaleAbbey(this);
    }
}
class HumanClericToken extends Token {

    public HumanClericToken() {
        super("Human Cleric", "1/1 white and black Human Cleric creature token");
        cardType.add(CardType.CREATURE);
        subtype.add("Human");
        subtype.add("Cleric");
        color.setWhite(true);
        color.setBlack(true);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }
}
