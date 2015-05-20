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
package mage.sets.urzassaga;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.ExchangeControlTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class GildedDrake extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature an opponent controls");
    
    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }
          
    public GildedDrake(UUID ownerId) {
        super(ownerId, 76, "Gilded Drake", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.expansionSetCode = "USG";
        this.subtype.add("Drake");

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Gilded Drake enters the battlefield, exchange control of Gilded Drake and up to one target creature an opponent controls. If you don't make an exchange, sacrifice Gilded Drake. This ability can't be countered except by spells and abilities.
        Ability ability = new EntersBattlefieldTriggeredAbility(new GildedDrakeEffect());
        ability.addTarget(new TargetCreaturePermanent(0,1,filter, false));
        this.addAbility(ability);                
    }

    public GildedDrake(final GildedDrake card) {
        super(card);
    }

    @Override
    public GildedDrake copy() {
        return new GildedDrake(this);
    }
}

class GildedDrakeEffect extends OneShotEffect {
    
    public GildedDrakeEffect() {
        super(Outcome.GainControl);
        this.staticText = "exchange control of {this} and up to one target creature an opponent controls. If you don't make an exchange, sacrifice {this}. This ability can't be countered except by spells and abilities";
    }
    
    public GildedDrakeEffect(final GildedDrakeEffect effect) {
        super(effect);
    }
    
    @Override
    public GildedDrakeEffect copy() {
        return new GildedDrakeEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourceObject = game.getPermanent(source.getSourceId());
        Permanent targetPermanent;
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (targetPointer.getFirst(game, source) != null) {
                targetPermanent = game.getPermanent(targetPointer.getFirst(game, source));
                if (targetPermanent != null) {
                    ContinuousEffect effect = new ExchangeControlTargetEffect(Duration.EndOfGame, "", true);                    
                    effect.setTargetPointer(targetPointer);
                    game.addEffect(effect, source);
                } else {
                    sourceObject.sacrifice(source.getSourceId(), game);
                }
            }
            return true;
        }
        return false;
    }
}
