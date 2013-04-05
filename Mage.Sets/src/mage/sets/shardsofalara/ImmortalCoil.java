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
package mage.sets.shardsofalara;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.StateTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.effects.common.DrawCardControllerEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author Plopman
 */
public class ImmortalCoil extends CardImpl<ImmortalCoil> {

    public ImmortalCoil(UUID ownerId) {
        super(ownerId, 79, "Immortal Coil", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{2}{B}{B}");
        this.expansionSetCode = "ALA";

        this.color.setBlack(true);

        // {tap}, Exile two cards from your graveyard: Draw a card.
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new DrawCardControllerEffect(1), new TapSourceCost());
        ability.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(2, new FilterCard("cards from your graveyard"))));
        this.addAbility(ability);
        // If damage would be dealt to you, prevent that damage. Exile a card from your graveyard for each 1 damage prevented this way.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new PreventAllDamageToControllerEffect()));        
        // When there are no cards in your graveyard, you lose the game.
        this.addAbility(new ImmortalCoilAbility());
    }

    public ImmortalCoil(final ImmortalCoil card) {
        super(card);
    }

    @Override
    public ImmortalCoil copy() {
        return new ImmortalCoil(this);
    }
}

class ImmortalCoilAbility extends StateTriggeredAbility<ImmortalCoilAbility> {

    public ImmortalCoilAbility() {
        super(Constants.Zone.BATTLEFIELD, new SacrificeSourceEffect());
        this.addEffect(new LoseGameEffect());
    }

    public ImmortalCoilAbility(final ImmortalCoilAbility ability) {
        super(ability);
    }

    @Override
    public ImmortalCoilAbility copy() {
        return new ImmortalCoilAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Player player = game.getPlayer(this.getControllerId());
        if(player != null && player.getGraveyard().size() == 0){
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When there are no cards in your graveyard, you lose the game";
    }

}

class LoseGameEffect extends OneShotEffect<LoseGameEffect> {

    public LoseGameEffect() {
        super(Constants.Outcome.Neutral);
        staticText = "you lose the game";
    }

    public LoseGameEffect(final LoseGameEffect effect) {
        super(effect);
    }

    @Override
    public LoseGameEffect copy() {
        return new LoseGameEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) { 
            player.lost(game);
            return true;
        }
        return false;
    }
}

class PreventAllDamageToControllerEffect extends PreventionEffectImpl<PreventAllDamageToControllerEffect> {


    public PreventAllDamageToControllerEffect() {
        super(Constants.Duration.WhileOnBattlefield);
        staticText = "If damage would be dealt to you, prevent that damage. Exile a card from your graveyard for each 1 damage prevented this way";
    }

    public PreventAllDamageToControllerEffect(final PreventAllDamageToControllerEffect effect) {
        super(effect);
    }

    @Override
    public PreventAllDamageToControllerEffect copy() {
        return new PreventAllDamageToControllerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        GameEvent preventEvent = new GameEvent(GameEvent.EventType.PREVENT_DAMAGE, source.getFirstTarget(), source.getId(), source.getControllerId(), event.getAmount(), false);
        if (!game.replaceEvent(preventEvent)) {
            int damage = event.getAmount();
            Player player = game.getPlayer(source.getControllerId());
            if(player != null){
                TargetCardInYourGraveyard target = new TargetCardInYourGraveyard(Math.min(damage, player.getGraveyard().size()), new FilterCard()); 
                target.setRequired(true);
                if (target.choose(Constants.Outcome.Exile, source.getControllerId(), source.getId(), game)) {
                    for (UUID targetId: target.getTargets()) {
                        Card card = player.getGraveyard().get(targetId, game);
                        if (card != null) {
                            card.moveToZone(Constants.Zone.EXILED, source.getSourceId(), game, false);
                        }
                    }
                }
            }
            event.setAmount(0);
            game.fireEvent(GameEvent.getEvent(GameEvent.EventType.PREVENTED_DAMAGE, source.getFirstTarget(), source.getId(), source.getControllerId(), damage));
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)) {
            if (event.getTargetId().equals(source.getControllerId())){
                return true;
            }

        }
        return false;
    }

}
