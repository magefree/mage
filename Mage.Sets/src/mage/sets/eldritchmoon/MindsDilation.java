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
package mage.sets.eldritchmoon;

import java.util.List;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.SpellsCastWatcher;

/**
 *
 * @author fireshoes
 */
public class MindsDilation extends CardImpl {

    public MindsDilation(UUID ownerId) {
        super(ownerId, 70, "Mind's Dilation", Rarity.MYTHIC, new CardType[]{CardType.ENCHANTMENT}, "{5}{U}{U}");
        this.expansionSetCode = "EMN";

        // Whenever an opponent casts his or her first spell each turn, that player exiles the top card of his or her library. If it's a nonland card,
        // you may cast it without paying its mana cost.
        Ability ability = new MindsDilationTriggeredAbility(new MindsDilationEffect(), false);
        this.addAbility(ability, new SpellsCastWatcher());
    }

    public MindsDilation(final MindsDilation card) {
        super(card);
    }

    @Override
    public MindsDilation copy() {
        return new MindsDilation(this);
    }
}

class MindsDilationTriggeredAbility extends SpellCastOpponentTriggeredAbility {

    public MindsDilationTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, new FilterSpell(), optional);
    }

    public MindsDilationTriggeredAbility(SpellCastOpponentTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SpellCastOpponentTriggeredAbility copy() {
        return new MindsDilationTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (super.checkTrigger(event, game)) {
            SpellsCastWatcher watcher = (SpellsCastWatcher) game.getState().getWatchers().get(SpellsCastWatcher.class.getName());
            if (watcher != null) {
                List<Spell> spells = watcher.getSpellsCastThisTurn(event.getPlayerId());
                if (spells != null && spells.size() == 1) {
                    for (Effect effect : getEffects()) {
                        effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an opponent casts his or her first spell each turn, that player exiles the top card of his or her library."
                + " If it's a nonland card, you may cast it without paying its mana cost";
    }
}

class MindsDilationEffect extends OneShotEffect {

    MindsDilationEffect() {
        super(Outcome.Benefit);
        this.staticText = "that player exiles the top card of his or her library. If it's a nonland card, you may cast it without paying its mana cost.";
    }

    MindsDilationEffect(final MindsDilationEffect effect) {
        super(effect);
    }

    @Override
    public MindsDilationEffect copy() {
        return new MindsDilationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller != null && sourceObject != null && opponent != null) {
            if (opponent.getLibrary().size() > 0) {
                Card card = opponent.getLibrary().removeFromTop(game);
                if (card != null) {
                    opponent.moveCardToExileWithInfo(card, source.getSourceId(), sourceObject.getIdName(), source.getSourceId(), game, Zone.LIBRARY, true);
                    ContinuousEffect effect = new MindsDilationCastFromExileEffect();
                    effect.setTargetPointer(new FixedTarget(card.getId()));
                    game.addEffect(effect, source);
                }
            }
            return true;
        }
        return false;
    }
}

class MindsDilationCastFromExileEffect extends AsThoughEffectImpl {

    MindsDilationCastFromExileEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "If it's a nonland card, you may cast it without paying its mana cost";
    }

    MindsDilationCastFromExileEffect(final MindsDilationCastFromExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public MindsDilationCastFromExileEffect copy() {
        return new MindsDilationCastFromExileEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        UUID targetId = getTargetPointer().getFirst(game, source);
        Player controller = game.getPlayer(source.getControllerId());
        if (targetId != null && controller != null) {
            Card card = game.getCard(targetId);
            if (card != null && !card.getCardType().contains(CardType.LAND) && game.getState().getZone(targetId) == Zone.EXILED) {
                if (controller.chooseUse(outcome, "Cast " + card.getLogName() + "?", source, game)) {
                    controller.cast(card.getSpellAbility(), game, true);
                }
                return true;
            }
        }
        return false;
    }
}