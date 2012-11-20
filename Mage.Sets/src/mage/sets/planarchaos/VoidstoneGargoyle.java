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
package mage.sets.planarchaos;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.repository.CardRepository;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 *
 * @author Plopman
 */
public class VoidstoneGargoyle extends CardImpl<VoidstoneGargoyle> {

    public VoidstoneGargoyle(UUID ownerId) {
        super(ownerId, 21, "Voidstone Gargoyle", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");
        this.expansionSetCode = "PLC";
        this.subtype.add("Gargoyle");

        this.color.setWhite(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // As Voidstone Gargoyle enters the battlefield, name a nonland card.
        this.addAbility(new AsEntersBattlefieldAbility(new VoidstoneGargoyleChooseCardEffect()));
        // The named card can't be cast.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new VoidstoneGargoyleReplacementEffect1()));
        // Activated abilities of sources with the chosen name can't be activated.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new VoidstoneGargoyleReplacementEffect2()));
    }

    public VoidstoneGargoyle(final VoidstoneGargoyle card) {
        super(card);
    }

    @Override
    public VoidstoneGargoyle copy() {
        return new VoidstoneGargoyle(this);
    }
}

class VoidstoneGargoyleChooseCardEffect extends OneShotEffect<VoidstoneGargoyleChooseCardEffect> {

    public VoidstoneGargoyleChooseCardEffect() {
        super(Constants.Outcome.Detriment);
        staticText = "name a nonland card";
    }

    public VoidstoneGargoyleChooseCardEffect(final VoidstoneGargoyleChooseCardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Choice cardChoice = new ChoiceImpl();
            cardChoice.setChoices(CardRepository.instance.getNonLandNames());
            cardChoice.clearChoice();
            while (!controller.choose(Constants.Outcome.Detriment, cardChoice, game)) {
                game.debugMessage("player canceled choosing name. retrying.");
            }
            String cardName = cardChoice.getChoice();
            game.informPlayers("VoidstoneGargoyle, named card: [" + cardName + "]");
            game.getState().setValue(source.getSourceId().toString(), cardName);
        }        
        return false;
    }

    @Override
    public VoidstoneGargoyleChooseCardEffect copy() {
        return new VoidstoneGargoyleChooseCardEffect(this);
    }

}

class VoidstoneGargoyleReplacementEffect1 extends ReplacementEffectImpl<VoidstoneGargoyleReplacementEffect1> {

    public VoidstoneGargoyleReplacementEffect1() {
        super(Constants.Duration.WhileOnBattlefield, Constants.Outcome.Detriment);
        staticText = "The named card can't be cast";
    }

    public VoidstoneGargoyleReplacementEffect1(final VoidstoneGargoyleReplacementEffect1 effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public VoidstoneGargoyleReplacementEffect1 copy() {
        return new VoidstoneGargoyleReplacementEffect1(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.CAST_SPELL) {
            MageObject object = game.getObject(event.getSourceId());
            if (object != null && object.getName().equals(game.getState().getValue(source.getSourceId().toString()))) {
                return true;
            }
        }
        return false;
    }

}

class VoidstoneGargoyleReplacementEffect2 extends ReplacementEffectImpl<VoidstoneGargoyleReplacementEffect2> {

    public VoidstoneGargoyleReplacementEffect2() {
        super(Constants.Duration.WhileOnBattlefield, Constants.Outcome.Detriment);
        staticText = "Activated abilities of sources with the chosen name can't be activated.";
    }

    public VoidstoneGargoyleReplacementEffect2(final VoidstoneGargoyleReplacementEffect2 effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public VoidstoneGargoyleReplacementEffect2 copy() {
        return new VoidstoneGargoyleReplacementEffect2(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.ACTIVATE_ABILITY) {
            MageObject object = game.getObject(event.getSourceId());
            if (object != null && object.getName().equals(game.getState().getValue(source.getSourceId().toString()))) {
                return true;
            }
        }
        return false;
    }

}