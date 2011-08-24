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
package mage.sets.magic2010;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtEndOfTurnDelayedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author North
 */
public class DragonWhelp extends CardImpl<DragonWhelp> {

    public DragonWhelp(UUID ownerId) {
        super(ownerId, 133, "Dragon Whelp", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");
        this.expansionSetCode = "M10";
        this.subtype.add("Dragon");

        this.color.setRed(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        this.addAbility(FlyingAbility.getInstance());
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new BoostSourceEffect(1, 0, Duration.EndOfTurn),
                new ManaCostsImpl("{R}"));
        ability.addEffect(new DragonWhelpEffect());
        this.addAbility(ability);
    }

    public DragonWhelp(final DragonWhelp card) {
        super(card);
    }

    @Override
    public DragonWhelp copy() {
        return new DragonWhelp(this);
    }
}

class DragonWhelpEffect extends OneShotEffect<DragonWhelpEffect> {

    public DragonWhelpEffect() {
        super(Outcome.Damage);
        this.staticText = "If this ability has been activated four or more times this turn, sacrifice {this} at the beginning of the next end step";
    }

    public DragonWhelpEffect(final DragonWhelpEffect effect) {
        super(effect);
    }

    @Override
    public DragonWhelpEffect copy() {
        return new DragonWhelpEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Integer amount = (Integer) game.getState().getValue(source.getSourceId().toString() + "DragonWhelp");
        if (amount == null) {
            amount = 0;
            DelayedTriggeredAbility delayedAbility = new AtEndOfTurnDelayedTriggeredAbility(new DragonWhelpDelayedEffect());
            delayedAbility.setSourceId(source.getSourceId());
            delayedAbility.setControllerId(source.getControllerId());
            game.addDelayedTriggeredAbility(delayedAbility);
        }
        amount++;
        game.getState().setValue(source.getSourceId().toString() + "DragonWhelp", amount);

        return true;
    }
}

class DragonWhelpDelayedEffect extends OneShotEffect<DragonWhelpDelayedEffect> {

    public DragonWhelpDelayedEffect() {
        super(Outcome.Damage);
        this.staticText = "If this ability has been activated four or more times this turn, sacrifice {this} at the beginning of the next end step";
    }

    public DragonWhelpDelayedEffect(final DragonWhelpDelayedEffect effect) {
        super(effect);
    }

    @Override
    public DragonWhelpDelayedEffect copy() {
        return new DragonWhelpDelayedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Integer amount = (Integer) game.getState().getValue(source.getSourceId().toString() + "DragonWhelp");
        if (amount != null && amount >= 4) {
            Permanent permanent = game.getPermanent(source.getSourceId());
            if (permanent != null) {
                permanent.sacrifice(source.getSourceId(), game);
            }
        }
        game.getState().setValue(source.getSourceId().toString() + "DragonWhelp", null);

        return true;
    }
}
