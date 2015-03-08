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
package mage.sets.modernmasters;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.CastFromHandCondition;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.SourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;
import mage.watchers.common.CastFromHandWatcher;

/**
 *
 * @author LevelX2
 */
public class Epochrasite extends CardImpl {

    public Epochrasite(UUID ownerId) {
        super(ownerId, 205, "Epochrasite", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");
        this.expansionSetCode = "MMA";
        this.subtype.add("Construct");

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Epochrasite enters the battlefield with three +1/+1 counters on it if you didn't cast it from your hand.
        this.addAbility(new EntersBattlefieldAbility(
                    new AddCountersSourceEffect(CounterType.P1P1.createInstance(3)),
                    new InvertCondition(new CastFromHandCondition()), true,
                    "{this} enters the battlefield with three +1/+1 counters on it if you didn't cast it from your hand",""), 
                new CastFromHandWatcher());

        // When Epochrasite dies, exile it with three time counters on it and it gains suspend.
        this.addAbility(new DiesTriggeredAbility(new EpochrasiteEffect()));
    }

    public Epochrasite(final Epochrasite card) {
        super(card);
    }

    @Override
    public Epochrasite copy() {
        return new Epochrasite(this);
    }
}

class EpochrasiteEffect extends OneShotEffect {

    public EpochrasiteEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile it with three time counters on it and it gains suspend";
    }

    public EpochrasiteEffect(final EpochrasiteEffect effect) {
        super(effect);
    }

    @Override
    public EpochrasiteEffect copy() {
        return new EpochrasiteEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = game.getCard(source.getSourceId());
        if (controller != null && card != null) {
            if (game.getState().getZone(card.getId()).equals(Zone.GRAVEYARD)) {
                UUID exileId = SuspendAbility.getSuspendExileId(controller.getId(), game);
                controller.moveCardToExileWithInfo(card, exileId, "Suspended cards of " + controller.getName(), source.getSourceId(), game, Zone.GRAVEYARD);
                card.addCounters(CounterType.TIME.createInstance(3), game);
                game.addEffect(new GainSuspendEffect(), source);
            }
            return true;
        }
        return false;
    }
}

class GainSuspendEffect extends ContinuousEffectImpl implements SourceEffect {

    public GainSuspendEffect() {
        super(Duration.Custom, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "{this} gains suspend";
    }

    public GainSuspendEffect(final GainSuspendEffect effect) {
        super(effect);
    }

    @Override
    public GainSuspendEffect copy() {
        return new GainSuspendEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(source.getSourceId());
        if (card != null && game.getState().getZone(card.getId()).equals(Zone.EXILED)) {
            SuspendAbility.addSuspendTemporaryToCard(card, source, game);
        } else {
            discard();
        }
        return true;
    }
}
