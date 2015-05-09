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
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.token.MinionToken;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author FenrisulfrX
 */
public class PhyrexianProcessor extends CardImpl {

    public PhyrexianProcessor(UUID ownerId) {
        super(ownerId, 306, "Phyrexian Processor", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{4}");
        this.expansionSetCode = "USG";

        // As {this} enters the battlefield, pay any amount of life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new PhyrexianProcessorEffect()));
        // {4}, {tap}: Put an X/X black Minion creature token onto the battlefield, where X is the life paid as {this} entered the battlefield.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PhyrexianProcessorCreateTokenEffect(), new ManaCostsImpl("{4}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    public PhyrexianProcessor(final PhyrexianProcessor card) {
        super(card);
    }

    @Override
    public PhyrexianProcessor copy() {
        return new PhyrexianProcessor(this);
    }
}

class PhyrexianProcessorEffect extends OneShotEffect {

    public PhyrexianProcessorEffect() {
        super(Outcome.LoseLife);
        staticText = "Pay any amount of life.";
    }

    public PhyrexianProcessorEffect(final PhyrexianProcessorEffect effect) {
        super(effect);
    }

    @Override
    public PhyrexianProcessorEffect copy() {
        return new PhyrexianProcessorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if(controller != null) {
            Card sourceCard = game.getCard(source.getSourceId());
            int payAmount = controller.getAmount(0, controller.getLife(), staticText, game);
            controller.loseLife(payAmount, game);
            game.informPlayers(new StringBuilder(sourceCard.getName()).append(": ").append(controller.getLogName())
                .append(" pays ").append(payAmount).append(" life.").toString());
            String key = CardUtil.getCardZoneString("lifePaid", source.getSourceId(), game);
            game.getState().setValue(key, payAmount);
            return true;
        }
        return false;
    }
}

class PhyrexianProcessorCreateTokenEffect extends OneShotEffect {
    
    public PhyrexianProcessorCreateTokenEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Put an X/X black Minion creature token onto the battlefield";
    }

    public PhyrexianProcessorCreateTokenEffect(PhyrexianProcessorCreateTokenEffect ability) {
        super(ability);
    }
    
    @Override
    public PhyrexianProcessorCreateTokenEffect copy() {
        return new PhyrexianProcessorCreateTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        String key = CardUtil.getCardZoneString("lifePaid", source.getSourceId(), game);
        Object object = game.getState().getValue(key);
        if(object != null && object instanceof Integer) {
            int lifePaid = (int) object;
            MinionToken token = new MinionToken();
            token.getPower().initValue(lifePaid);
            token.getToughness().initValue(lifePaid);
            token.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId());
            return true;
        }
        return false;
    }
}