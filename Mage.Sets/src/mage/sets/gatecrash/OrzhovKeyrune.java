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
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continious.BecomesCreatureSourceEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.game.permanent.token.Token;

/**
 *
 * @author LevelX2
 */
public class OrzhovKeyrune extends CardImpl<OrzhovKeyrune> {

    public OrzhovKeyrune(UUID ownerId) {
        super(ownerId, 233, "Orzhov Keyrune", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.expansionSetCode = "GTC";

        // {{T}: Add {W} or {B} to your mana pool.
        this.addAbility(new WhiteManaAbility());
        this.addAbility(new BlackManaAbility());

        // {W}{B}: Orzhov Keyrune becomes a 1/4 white and black Thrull artifact creature with lifelink until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesCreatureSourceEffect(new OrzhovKeyruneToken(), "", Duration.EndOfTurn), new ManaCostsImpl("{W}{B}")));
    }

    public OrzhovKeyrune(final OrzhovKeyrune card) {
        super(card);
    }

    @Override
    public OrzhovKeyrune copy() {
        return new OrzhovKeyrune(this);
    }

    private class OrzhovKeyruneToken extends Token {
        OrzhovKeyruneToken() {
            super("Thrull", "1/4 white and black Thrull artifact creature with lifelink");
            cardType.add(CardType.ARTIFACT);
            cardType.add(CardType.CREATURE);
            color.setWhite(true);
            color.setBlack(true);
            subtype.add("Thrull");
            power = new MageInt(1);
            toughness = new MageInt(4);
            this.addAbility(LifelinkAbility.getInstance());
        }
    }
}
