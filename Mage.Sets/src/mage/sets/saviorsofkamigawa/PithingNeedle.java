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
package mage.sets.saviorsofkamigawa;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.sets.Sets;

/**
 *
 * @author noxx and jeffwadsworth
 */
public class PithingNeedle extends CardImpl<PithingNeedle> {

    public PithingNeedle(UUID ownerId) {
        super(ownerId, 158, "Pithing Needle", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{1}");
        this.expansionSetCode = "SOK";

        // As Pithing Needle enters the battlefield, name a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new NameCard()));
        
        // Activated abilities of sources with the chosen name can't be activated unless they're mana abilities.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new PithingNeedleEffect()));
    }

    public PithingNeedle(final PithingNeedle card) {
        super(card);
    }

    @Override
    public PithingNeedle copy() {
        return new PithingNeedle(this);
    }
}

class NameCard extends OneShotEffect<NameCard> {

    public NameCard() {
        super(Constants.Outcome.Detriment);
        staticText = "name a card";
    }

    public NameCard(final NameCard effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Choice cardChoice = new ChoiceImpl();
            cardChoice.setChoices(Sets.getCardNames());
            cardChoice.clearChoice();
            while (!controller.choose(Constants.Outcome.Detriment, cardChoice, game)) {
                game.debugMessage("player canceled choosing name. retrying.");
            }
            String cardName = cardChoice.getChoice();
            game.informPlayers("Pithing Needle, named card: [" + cardName + "]");
            game.getState().setValue(source.getSourceId().toString(), cardName);
        }        
        return false;
    }

    @Override
    public NameCard copy() {
        return new NameCard(this);
    }

}

class PithingNeedleEffect extends ReplacementEffectImpl<PithingNeedleEffect> {

    public PithingNeedleEffect() {
        super(Constants.Duration.WhileOnBattlefield, Constants.Outcome.Detriment);
        staticText = "Activated abilities of sources with the chosen name can't be activated unless they're mana abilities";
    }

    public PithingNeedleEffect(final PithingNeedleEffect effect) {
        super(effect);
    }

    @Override
    public PithingNeedleEffect copy() {
        return new PithingNeedleEffect(this);
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
        if (event.getType() == GameEvent.EventType.ACTIVATE_ABILITY) {
            MageObject object = game.getObject(event.getSourceId());
            Ability ability = game.getAbility(event.getTargetId(), event.getSourceId());
            if (ability != null && object != null) {
                if (ability.getAbilityType() != Constants.AbilityType.MANA &&
                    object.getName().equals(game.getState().getValue(source.getSourceId().toString()))) {
                        return true;
                }
            }
        }
        return false;
    }
}

