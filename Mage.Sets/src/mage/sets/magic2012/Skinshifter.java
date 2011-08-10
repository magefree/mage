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
package mage.sets.magic2012;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.ActivateOncePerTurnActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continious.BecomesCreatureSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.game.permanent.token.Token;

/**
 *
 * @author North
 */
public class Skinshifter extends CardImpl<Skinshifter> {

    public Skinshifter(UUID ownerId) {
        super(ownerId, 195, "Skinshifter", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.expansionSetCode = "M12";
        this.subtype.add("Human");
        this.subtype.add("Shaman");

        this.color.setGreen(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        Ability ability = new ActivateOncePerTurnActivatedAbility(Zone.BATTLEFIELD,
                new BecomesCreatureSourceEffect(new RhinoToken(), "", Duration.EndOfTurn),
                new ManaCostsImpl("{G}"));

        Mode mode = new Mode();
        mode.getEffects().add(new BecomesCreatureSourceEffect(new BirdToken(), "", Duration.EndOfTurn));
        ability.addMode(mode);

        mode = new Mode();
        mode.getEffects().add(new BecomesCreatureSourceEffect(new PlantToken(), "", Duration.EndOfTurn));
        ability.addMode(mode);

        this.addAbility(ability);
    }

    public Skinshifter(final Skinshifter card) {
        super(card);
    }

    @Override
    public Skinshifter copy() {
        return new Skinshifter(this);
    }

    private class RhinoToken extends Token {

        public RhinoToken() {
            super("Rhino", "4/4 Rhino with trample");
            this.cardType.add(CardType.CREATURE);
            this.subtype.add("Rhino");

            this.color.setGreen(true);
            this.power = new MageInt(4);
            this.toughness = new MageInt(4);
            this.addAbility(TrampleAbility.getInstance());
        }
    }

    private class BirdToken extends Token {

        public BirdToken() {
            super("Bird", "2/2 Bird with flying");
            this.cardType.add(CardType.CREATURE);
            this.subtype.add("Bird");

            this.color.setGreen(true);
            this.power = new MageInt(2);
            this.toughness = new MageInt(2);
            this.addAbility(FlyingAbility.getInstance());
        }
    }

    private class PlantToken extends Token {

        public PlantToken() {
            super("Plant", "0/8 Plant");
            this.cardType.add(CardType.CREATURE);
            this.subtype.add("Plant");

            this.color.setGreen(true);
            this.power = new MageInt(0);
            this.toughness = new MageInt(8);
        }
    }
}
