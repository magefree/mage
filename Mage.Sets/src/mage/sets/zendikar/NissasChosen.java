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
package mage.sets.zendikar;

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
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;

/**
 *
 * @author maurer.it_at_gmail.com
 */
public class NissasChosen extends CardImpl<NissasChosen> {

    public NissasChosen(UUID ownerId) {
        super(ownerId, 171, "Nissa's Chosen", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{G}{G}");
        this.expansionSetCode = "ZEN";
        this.subtype.add("Elf");
        this.subtype.add("Warrior");

        this.color.setGreen(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        this.addAbility(new SimpleStaticAbility(Zone.ALL, new NissasChosenEffect()));
    }

    public NissasChosen(final NissasChosen card) {
        super(card);
    }

    @Override
    public NissasChosen copy() {
        return new NissasChosen(this);
    }
}

class NissasChosenEffect extends ReplacementEffectImpl<NissasChosenEffect> {

    boolean onTop = false;

    public NissasChosenEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If {this} would be put into a graveyard from the battlefield, put it on the bottom of its owner's library instead";
    }

    public NissasChosenEffect(final NissasChosenEffect effect) {
        super(effect);
        this.onTop = effect.onTop;
    }

    @Override
    public NissasChosenEffect copy() {
        return new NissasChosenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Card card = game.getCard(event.getTargetId());
        if ( card != null && event.getTargetId().equals(source.getSourceId()) ) {
            return card.moveToZone(Zone.LIBRARY, source.getId(), game, onTop);
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if ( event.getType() == EventType.ZONE_CHANGE )
        {
                    ZoneChangeEvent zEvent = (ZoneChangeEvent)event;
                    if ( zEvent.getFromZone() == Zone.BATTLEFIELD && zEvent.getToZone() == Zone.GRAVEYARD ) {
                        return true;
                    }
        }
        return false;
    }

}