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
package mage.sets.dissension;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ForecastAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.filter.common.FilterCreaturePermanent;
/**
 *
 * @author fireshoes
 */
public class PrideOfTheClouds extends CardImpl {
    
    public PrideOfTheClouds(UUID ownerId) {
        super(ownerId, 125, "Pride of the Clouds", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{W}{U}");
        this.expansionSetCode = "DIS";
        this.subtype.add("Elemental");
        this.subtype.add("Cat");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Pride of the Clouds gets +1/+1 for each other creature with flying on the battlefield.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PrideOfTheCloudsEffect()));
        // Forecast - {2}{W}{U}, Reveal Pride of the Clouds from your hand: Put a 1/1 white and blue Bird creature token with flying onto the battlefield.
        this.addAbility(new ForecastAbility(new CreateTokenEffect(new BirdToken()), new ManaCostsImpl("{2}{W}{U}")));
    }

    public PrideOfTheClouds(final PrideOfTheClouds card) {
        super(card);
    }

    @Override
    public PrideOfTheClouds copy() {
        return new PrideOfTheClouds(this);
    }

    private class BirdToken extends Token {

        public BirdToken() {
            super("Bird", "1/1 white snf blue Bird creature token with flying");
            cardType.add(CardType.CREATURE);
            color.setWhite(true);
            color.setBlue(true);
            subtype.add("Bird");
            power = new MageInt(1);
            toughness = new MageInt(1);
            addAbility(FlyingAbility.getInstance());
        }
        
    }

class PrideOfTheCloudsEffect extends ContinuousEffectImpl {

        public PrideOfTheCloudsEffect() {
            super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
            staticText = "{this} gets +1/+1 for each other creature with flying on the battlefield";
        }

        public PrideOfTheCloudsEffect(final PrideOfTheCloudsEffect effect) {
            super(effect);
        }

        @Override
        public PrideOfTheCloudsEffect copy() {
            return new PrideOfTheCloudsEffect(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            FilterCreaturePermanent filter = new FilterCreaturePermanent();
            int count = game.getBattlefield().count(filter, source.getSourceId(), source.getControllerId(), game) - 1;
            if (count > 0) {
                Permanent target = (Permanent) game.getPermanent(source.getSourceId());
                if (target != null) {
                    target.addPower(count);
                    target.addToughness(count);
                    return true;
                }
            }
            return false;
        } 
    }
}
