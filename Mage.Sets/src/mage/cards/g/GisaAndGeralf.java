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
package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.PutTopCardOfLibraryIntoGraveControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.Watcher;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public class GisaAndGeralf extends CardImpl {

    public GisaAndGeralf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}{B}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add("Human");
        this.subtype.add("Wizard");
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Gisa and Geralf enters the battlefield, put the top four cards of your library into your graveyard.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new PutTopCardOfLibraryIntoGraveControllerEffect(4)));

        // During each of your turns, you may cast a Zombie creature card from your graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GisaAndGeralfContinuousEffect()), new GisaAndGeralfWatcher());
    }

    public GisaAndGeralf(final GisaAndGeralf card) {
        super(card);
    }

    @Override
    public GisaAndGeralf copy() {
        return new GisaAndGeralf(this);
    }
}

class GisaAndGeralfContinuousEffect extends ContinuousEffectImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("Zombie creature card");

    static {
        filter.add(new SubtypePredicate("Zombie"));
    }

    GisaAndGeralfContinuousEffect() {
        super(Duration.WhileOnBattlefield, Layer.PlayerEffects, SubLayer.NA, Outcome.Benefit);
        staticText = "During each of your turns, you may cast a Zombie creature card from your graveyard";
    }

    GisaAndGeralfContinuousEffect(final GisaAndGeralfContinuousEffect effect) {
        super(effect);
    }

    @Override
    public GisaAndGeralfContinuousEffect copy() {
        return new GisaAndGeralfContinuousEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            for (Card card : player.getGraveyard().getCards(filter, game)) {
                ContinuousEffect effect = new GisaAndGeralfCastFromGraveyardEffect();
                effect.setTargetPointer(new FixedTarget(card.getId()));
                game.addEffect(effect, source);
            }
            return true;
        }
        return false;
    }
}

class GisaAndGeralfCastFromGraveyardEffect extends AsThoughEffectImpl {

    GisaAndGeralfCastFromGraveyardEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "You may cast a Zombie creature card from your graveyard";
    }

    GisaAndGeralfCastFromGraveyardEffect(final GisaAndGeralfCastFromGraveyardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public GisaAndGeralfCastFromGraveyardEffect copy() {
        return new GisaAndGeralfCastFromGraveyardEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (objectId.equals(getTargetPointer().getFirst(game, source))) {
            if (affectedControllerId.equals(source.getControllerId())) {
                GisaAndGeralfWatcher watcher = (GisaAndGeralfWatcher) game.getState().getWatchers().get("GisaAndGeralfWatcher", source.getSourceId());
                return !watcher.isAbilityUsed();
            }
        }
        return false;
    }
}

class GisaAndGeralfWatcher extends Watcher {

    boolean abilityUsed = false;

    GisaAndGeralfWatcher() {
        super("GisaAndGeralfWatcher", WatcherScope.CARD);
    }

    GisaAndGeralfWatcher(final GisaAndGeralfWatcher watcher) {
        super(watcher);
        this.abilityUsed = watcher.abilityUsed;
    }

    @Override
    public void watch(GameEvent event, Game game) {
         if (event.getType() == GameEvent.EventType.SPELL_CAST && event.getZone() == Zone.GRAVEYARD) {
            Spell spell = (Spell) game.getObject(event.getTargetId());
            if (spell.isCreature() && spell.getSubtype(game).contains("Zombie")) {
               abilityUsed = true;
            }
        }
    }

    @Override
    public GisaAndGeralfWatcher copy() {
        return new GisaAndGeralfWatcher(this);
    }

    @Override
    public void reset() {
        super.reset();
        abilityUsed = false;
    }

    public boolean isAbilityUsed() {
        return abilityUsed;
    }
}