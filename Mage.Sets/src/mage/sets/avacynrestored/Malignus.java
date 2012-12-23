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
package mage.sets.avacynrestored;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continious.SetPowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author noxx
 */
public class Malignus extends CardImpl<Malignus> {

    public Malignus(UUID ownerId) {
        super(ownerId, 148, "Malignus", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");
        this.expansionSetCode = "AVR";
        this.subtype.add("Elemental");
        this.subtype.add("Spirit");

        this.color.setRed(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Malignus's power and toughness are each equal to half the highest life total among your opponents, rounded up.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.ALL, new SetPowerToughnessSourceEffect(new HighestLifeTotalAmongOpponentsCount(), Constants.Duration.EndOfGame)));

        // Damage that would be dealt by Malignus can't be prevented.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new MalignusEffect()));
    }

    public Malignus(final Malignus card) {
        super(card);
    }

    @Override
    public Malignus copy() {
        return new Malignus(this);
    }
}

class HighestLifeTotalAmongOpponentsCount implements DynamicValue {
    @Override
    public int calculate(Game game, Ability sourceAbility) {
        if (sourceAbility != null) {
            Player controller = game.getPlayer(sourceAbility.getControllerId());
            if (controller != null) {
                int max = 0;
                for (UUID uuid : game.getOpponents(controller.getId())) {
                    Player opponent = game.getPlayer(uuid);
                    if (opponent != null) {
                        if (opponent.getLife() > max) {
                            max = opponent.getLife();
                        }
                    }
                }
                return (int)Math.ceil(max / 2.0);
            }
        }
        return 0;
    }

    @Override
    public DynamicValue copy() {
        return new CardsInControllerHandCount();
    }

    @Override
    public String getMessage() {
        return "half the highest life total among your opponents, rounded up";
    }

    @Override
    public String toString() {
        return "1";
    }
}

class MalignusEffect extends ReplacementEffectImpl<MalignusEffect> {

    public MalignusEffect() {
        super(Constants.Duration.WhileOnBattlefield, Constants.Outcome.Benefit);
        staticText = "Damage that would be dealt by {this} can't be prevented";
    }

    public MalignusEffect(final MalignusEffect effect) {
        super(effect);
    }

    @Override
    public MalignusEffect copy() {
        return new MalignusEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.PREVENT_DAMAGE && event.getSourceId().equals(source.getSourceId())) {
            return true;
        }
        return false;
    }

}
