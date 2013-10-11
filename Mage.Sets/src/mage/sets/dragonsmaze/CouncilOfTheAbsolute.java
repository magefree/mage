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

package mage.sets.dragonsmaze;

import java.util.UUID;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.CostModificationEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.repository.CardRepository;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CostModificationType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */


public class CouncilOfTheAbsolute extends CardImpl<CouncilOfTheAbsolute> {

    public CouncilOfTheAbsolute (UUID ownerId) {
        super(ownerId, 62, "Council of the Absolute", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{2}{W}{U}");
        this.expansionSetCode = "DGM";
        this.subtype.add("Human");
        this.subtype.add("Advisor");
        this.color.setWhite(true);
        this.color.setBlue(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // As Council of the Absolute enters the battlefield, name a card other than a creature or a land card.
        this.addAbility(new AsEntersBattlefieldAbility(new CouncilOfTheAbsoluteChooseCardEffect()));
        // Your opponents can't cast the chosen card.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CouncilOfTheAbsoluteReplacementEffect()));
        // Spells with the chosen name cost 2 less for you to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CouncilOfTheAbsoluteCostReductionEffect()));


    }

    public CouncilOfTheAbsolute (final CouncilOfTheAbsolute card) {
        super(card);
    }

    @Override
    public CouncilOfTheAbsolute copy() {
        return new CouncilOfTheAbsolute(this);
    }

}

class CouncilOfTheAbsoluteChooseCardEffect extends OneShotEffect<CouncilOfTheAbsoluteChooseCardEffect> {

    public CouncilOfTheAbsoluteChooseCardEffect() {
        super(Outcome.Detriment);
        staticText = "name a card other than a creature or a land card";
    }

    public CouncilOfTheAbsoluteChooseCardEffect(final CouncilOfTheAbsoluteChooseCardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Choice cardChoice = new ChoiceImpl();
            cardChoice.setChoices(CardRepository.instance.getNonLandAndNonCreatureNames());
            cardChoice.clearChoice();
            while (!controller.choose(Outcome.Detriment, cardChoice, game)) {
                game.debugMessage("player canceled choosing name. retrying.");
            }
            String cardName = cardChoice.getChoice();
            game.informPlayers("Council of the Absolute, named card: [" + cardName + "]");
            game.getState().setValue(source.getSourceId().toString(), cardName);
        }
        return false;
    }

    @Override
    public CouncilOfTheAbsoluteChooseCardEffect copy() {
        return new CouncilOfTheAbsoluteChooseCardEffect(this);
    }

}

class CouncilOfTheAbsoluteReplacementEffect extends ReplacementEffectImpl<CouncilOfTheAbsoluteReplacementEffect> {

    public CouncilOfTheAbsoluteReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "Your opponents can't cast the chosen card";
    }

    public CouncilOfTheAbsoluteReplacementEffect(final CouncilOfTheAbsoluteReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public CouncilOfTheAbsoluteReplacementEffect copy() {
        return new CouncilOfTheAbsoluteReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == EventType.CAST_SPELL && game.getOpponents(source.getControllerId()).contains(event.getPlayerId())) {
            MageObject object = game.getObject(event.getSourceId());
            if (object != null && object.getName().equals(game.getState().getValue(source.getSourceId().toString()))) {
                return true;
            }
        }
        return false;
    }
}

class CouncilOfTheAbsoluteCostReductionEffect extends CostModificationEffectImpl<CouncilOfTheAbsoluteCostReductionEffect> {

    public CouncilOfTheAbsoluteCostReductionEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        this.staticText = "Spells with the chosen name cost 2 less for you to cast";
    }

    protected CouncilOfTheAbsoluteCostReductionEffect(CouncilOfTheAbsoluteCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.reduceCost(abilityToModify, 2);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if ((abilityToModify instanceof SpellAbility || abilityToModify instanceof FlashbackAbility)
                && abilityToModify.getControllerId().equals(source.getControllerId())) {
            Card card = game.getCard(abilityToModify.getSourceId());
            return card.getName().equals(game.getState().getValue(source.getSourceId().toString()));
        }
        return false;
    }

    @Override
    public CouncilOfTheAbsoluteCostReductionEffect copy() {
        return new CouncilOfTheAbsoluteCostReductionEffect(this);
    }
}
