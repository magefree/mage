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
package mage.cards.f;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Library;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author Styxo
 */
public class FlamesOfRemembrance extends CardImpl {

    public FlamesOfRemembrance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{R}");

        // At the beggining of your upkeep, you may exile a card from your graveyard. If you do, put a lore counter on Flames of Remembrance.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new DoIfCostPaid(new AddCountersSourceEffect(CounterType.LORE.createInstance()), new ExileFromGraveCost(new TargetCardInYourGraveyard()), null, true), TargetController.YOU, false));

        // Sacrifice Flames of Remembrance: Exile top X cards of your library, where X is the number of lore counters on Flames of Remembrance. Until end of turn you play cards exile this way.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new FlamesOfRemembranceExileEffect(new CountersSourceCount(CounterType.LORE)), new SacrificeSourceCost()));
    }

    public FlamesOfRemembrance(final FlamesOfRemembrance card) {
        super(card);
    }

    @Override
    public FlamesOfRemembrance copy() {
        return new FlamesOfRemembrance(this);
    }
}

class FlamesOfRemembranceExileEffect extends OneShotEffect {

    private CountersSourceCount amount;

    public FlamesOfRemembranceExileEffect(CountersSourceCount amount) {
        super(Outcome.Benefit);
        this.amount = amount;
        this.staticText = "Exile top X cards of your library, where X is the number of lore counters on Flames of Remembrance. Until end of turn you play cards exile this way";
    }

    public FlamesOfRemembranceExileEffect(final FlamesOfRemembranceExileEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public FlamesOfRemembranceExileEffect copy() {
        return new FlamesOfRemembranceExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {

            Library library = controller.getLibrary();
            List<Card> cards = new ArrayList<>();
            int count = Math.min(amount.calculate(game, source, this), library.size());
            for (int i = 0; i < count; i++) {
                Card card = library.removeFromTop(game);
                if (card != null) {
                    cards.add(card);
                }
            }
            if (!cards.isEmpty()) {
                List<UUID> cardsId = new ArrayList<>();
                for (Card card : cards) {
                    card.moveToExile(source.getSourceId(), "Flames of Remembrance", source.getSourceId(), game);
                    cardsId.add(card.getId());
                }
                game.addEffect(new FlamesOfRemembranceMayPlayExiledEffect(cardsId), source);
            }
            return true;
        }
        return false;
    }

}

class FlamesOfRemembranceMayPlayExiledEffect extends AsThoughEffectImpl {

    public List<UUID> cards = new ArrayList<>();

    public FlamesOfRemembranceMayPlayExiledEffect(List<UUID> cards) {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
        this.cards.addAll(cards);
    }

    public FlamesOfRemembranceMayPlayExiledEffect(final FlamesOfRemembranceMayPlayExiledEffect effect) {
        super(effect);
        this.cards.addAll(effect.cards);
    }

    @Override
    public FlamesOfRemembranceMayPlayExiledEffect copy() {
        return new FlamesOfRemembranceMayPlayExiledEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        Card card = game.getCard(sourceId);
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && card != null && game.getState().getZone(sourceId) == Zone.EXILED) {
            if (cards.contains(sourceId)) {
                return true;
            }
        }
        return false;
    }

}
