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
package mage.sets.commander2013;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class NayaSoulbeast extends CardImpl<NayaSoulbeast> {

    public NayaSoulbeast(UUID ownerId) {
        super(ownerId, 157, "Naya Soulbeast", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{6}{G}{G}");
        this.expansionSetCode = "C13";
        this.subtype.add("Beast");

        this.color.setGreen(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // When you cast Naya Soulbeast, each player reveals the top card of his or her library. Naya Soulbeast enters the battlefield with X +1/+1 counters on it, where X is the total converted mana cost of all cards revealed this way.
        Ability ability = new CastSourceTriggeredAbility(new NayaSoulbeastCastEffect(), false);
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

class NayaSoulbeastCastEffect extends OneShotEffect<NayaSoulbeastCastEffect> {

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
        if (controller != null) {
            int cmc = 0;
            for (UUID playerId :controller.getInRange()) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    if (player.getLibrary().size() > 0) {
                        Card card = player.getLibrary().getFromTop(game);
                        cmc += card.getManaCost().convertedManaCost();
                        player.revealCards(new StringBuilder("Naya Soulbeast (").append(player.getName()).append(")").toString(), new CardsImpl(card), game);
                    }
                }
            }
            for (Effect effect : source.getEffects()) {
                if (effect instanceof NayaSoulbeastReplacementEffect) {
                    effect.setValue("NayaSoulbeastCounters", new Integer(cmc));
                }
            }
            return true;
        }
        return false;
    }
}

class NayaSoulbeastReplacementEffect extends ReplacementEffectImpl<NayaSoulbeastReplacementEffect> {

    public static final String SOURCE_CAST_SPELL_ABILITY = "sourceCastSpellAbility";

    public NayaSoulbeastReplacementEffect() {
        super(Duration.OneUse, Outcome.BoostCreature, true);
        staticText = "{this} enters the battlefield with X +1/+1 counters on it, where X is the total converted mana cost of all cards revealed this way";
    }

    public NayaSoulbeastReplacementEffect(final NayaSoulbeastReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == EventType.ENTERS_THE_BATTLEFIELD) {
            if (event.getTargetId().equals(source.getSourceId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Object object = this.getValue("NayaSoulbeastCounters");
        if (object instanceof Integer) {
            int amount = ((Integer)object).intValue();
            new AddCountersSourceEffect(CounterType.P1P1.createInstance(amount)).apply(game, source);
        }
        return false;
    }

    @Override
    public NayaSoulbeastReplacementEffect copy() {
        return new NayaSoulbeastReplacementEffect(this);
    }

}
