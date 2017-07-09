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
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class NayaSoulbeast extends CardImpl {

    public NayaSoulbeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}{G}{G}");
        this.subtype.add("Beast");

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // When you cast Naya Soulbeast, each player reveals the top card of his or her library.
        Ability ability = new CastSourceTriggeredAbility(new NayaSoulbeastCastEffect(), false);
        // Naya Soulbeast enters the battlefield with X +1/+1 counters on it, where X is the total converted mana cost of all cards revealed this way.
        ability.addEffect(new NayaSoulbeastReplacementEffect());
        this.addAbility(ability);
    }

    public NayaSoulbeast(final NayaSoulbeast card) {
        super(card);
    }

    @Override
    public NayaSoulbeast copy() {
        return new NayaSoulbeast(this);
    }
}

class NayaSoulbeastCastEffect extends OneShotEffect {

    public NayaSoulbeastCastEffect() {
        super(Outcome.Benefit);
        this.staticText = "each player reveals the top card of his or her library";
    }

    public NayaSoulbeastCastEffect(final NayaSoulbeastCastEffect effect) {
        super(effect);
    }

    @Override
    public NayaSoulbeastCastEffect copy() {
        return new NayaSoulbeastCastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            int cmc = 0;
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    if (player.getLibrary().hasCards()) {
                        Card card = player.getLibrary().getFromTop(game);
                        cmc += card.getConvertedManaCost();
                        player.revealCards(sourceObject.getName() + " (" + player.getName() + ')', new CardsImpl(card), game);
                    }
                }
            }
            for (Effect effect : source.getEffects()) {
                if (effect instanceof NayaSoulbeastReplacementEffect) {
                    effect.setValue("NayaSoulbeastCounters", cmc);
                }
            }
            return true;
        }
        return false;
    }
}

class NayaSoulbeastReplacementEffect extends ReplacementEffectImpl {

    public NayaSoulbeastReplacementEffect() {
        super(Duration.OneUse, Outcome.BoostCreature);
        staticText = "{this} enters the battlefield with X +1/+1 counters on it, where X is the total converted mana cost of all cards revealed this way";
    }

    public NayaSoulbeastReplacementEffect(final NayaSoulbeastReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getTargetId().equals(source.getSourceId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Object object = this.getValue("NayaSoulbeastCounters");
        Permanent permanent = game.getPermanentEntering(source.getSourceId());
        if (permanent != null && object instanceof Integer) {
            int amount = ((Integer) object);
            permanent.addCounters(CounterType.P1P1.createInstance(amount), source, game);
        }
        return false;
    }

    @Override
    public NayaSoulbeastReplacementEffect copy() {
        return new NayaSoulbeastReplacementEffect(this);
    }

}
