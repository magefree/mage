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
package mage.sets.alarareborn;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.repository.CardRepository;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;

/**
 *
 * @author Plopman
 */
public class MeddlingMage extends CardImpl<MeddlingMage> {

    public MeddlingMage(UUID ownerId) {
        super(ownerId, 8, "Meddling Mage", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{W}{U}");
        this.expansionSetCode = "ARB";
        this.subtype.add("Human");
        this.subtype.add("Wizard");

        this.color.setBlue(true);
        this.color.setWhite(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // As Meddling Mage enters the battlefield, name a nonland card.
        this.addAbility(new AsEntersBattlefieldAbility(new MeddlingMageChooseCardEffect()));

        //The named card can't be cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MeddlingMageReplacementEffect()));
    }

    public MeddlingMage(final MeddlingMage card) {
        super(card);
    }

    @Override
    public MeddlingMage copy() {
        return new MeddlingMage(this);
    }
}

class MeddlingMageChooseCardEffect extends OneShotEffect<MeddlingMageChooseCardEffect> {

    public MeddlingMageChooseCardEffect() {
        super(Outcome.Detriment);
        staticText = "name a nonland card";
    }

    public MeddlingMageChooseCardEffect(final MeddlingMageChooseCardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Choice cardChoice = new ChoiceImpl();
            cardChoice.setChoices(CardRepository.instance.getNonLandNames());
            cardChoice.clearChoice();
            while (!controller.choose(Outcome.Detriment, cardChoice, game)) {
                game.debugMessage("player canceled choosing name. retrying.");
            }
            String cardName = cardChoice.getChoice();
            game.informPlayers("MeddlingMage, named card: [" + cardName + "]");
            game.getState().setValue(source.getSourceId().toString(), cardName);
        }        
        return false;
    }

    @Override
    public MeddlingMageChooseCardEffect copy() {
        return new MeddlingMageChooseCardEffect(this);
    }

}

class MeddlingMageReplacementEffect extends ReplacementEffectImpl<MeddlingMageReplacementEffect> {

    public MeddlingMageReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "The named card can't be cast";
    }

    public MeddlingMageReplacementEffect(final MeddlingMageReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public MeddlingMageReplacementEffect copy() {
        return new MeddlingMageReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == EventType.CAST_SPELL) {
            MageObject object = game.getObject(event.getSourceId());
            if (object != null && object.getName().equals(game.getState().getValue(source.getSourceId().toString()))) {
                return true;
            }
        }
        return false;
    }

}