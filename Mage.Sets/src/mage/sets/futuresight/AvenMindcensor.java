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
package mage.sets.futuresight;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author LevelX2
 */
public class AvenMindcensor extends CardImpl<AvenMindcensor> {

    public AvenMindcensor(UUID ownerId) {
        super(ownerId, 18, "Aven Mindcensor", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.expansionSetCode = "FUT";
        this.subtype.add("Bird");
        this.subtype.add("Wizard");

        this.color.setWhite(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());
        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // If an opponent would search a library, that player searches the top four cards of that library instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AvenMindcensorEffect()));

    }

    public AvenMindcensor(final AvenMindcensor card) {
        super(card);
    }

    @Override
    public AvenMindcensor copy() {
        return new AvenMindcensor(this);
    }
}

class AvenMindcensorEffect extends ReplacementEffectImpl<AvenMindcensorEffect> {
    AvenMindcensorEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If an opponent would search a library, that player searches the top four cards of that library instead";
    }

    AvenMindcensorEffect(final AvenMindcensorEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        game.getState().setValue("LibrarySearchLimit", new Integer(4));
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.SEARCH_LIBRARY && game.getOpponents(source.getControllerId()).contains(event.getPlayerId())) {
                return true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public AvenMindcensorEffect copy() {
        return new AvenMindcensorEffect(this);
    }
}
