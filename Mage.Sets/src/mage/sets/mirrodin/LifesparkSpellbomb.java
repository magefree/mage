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
package mage.sets.mirrodin;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardControllerEffect;
import mage.abilities.effects.common.continious.BecomesCreatureTargetEffect;
import mage.cards.CardImpl;
import mage.game.permanent.token.Token;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author Loki
 */
public class LifesparkSpellbomb extends CardImpl<LifesparkSpellbomb> {

    public LifesparkSpellbomb(UUID ownerId) {
        super(ownerId, 197, "Lifespark Spellbomb", Rarity.COMMON, new CardType[]{CardType.ARTIFACT}, "{1}");
        this.expansionSetCode = "MRD";
        Ability firstAbility = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new BecomesCreatureTargetEffect(new LifesparkSpellbombToken(), "land", Duration.EndOfTurn), new ColoredManaCost(Constants.ColoredManaSymbol.G));
        firstAbility.addCost(new SacrificeSourceCost());
        firstAbility.addTarget(new TargetLandPermanent());
        this.addAbility(firstAbility);
        Ability secondAbility = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new DrawCardControllerEffect(1), new GenericManaCost(1));
        secondAbility.addCost(new SacrificeSourceCost());
        this.addAbility(secondAbility);
    }

    public LifesparkSpellbomb(final LifesparkSpellbomb card) {
        super(card);
    }

    @Override
    public LifesparkSpellbomb copy() {
        return new LifesparkSpellbomb(this);
    }

}

class LifesparkSpellbombToken extends Token {

    public LifesparkSpellbombToken() {
        super("", "3/3");
        this.cardType.add(CardType.CREATURE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
    }
}
