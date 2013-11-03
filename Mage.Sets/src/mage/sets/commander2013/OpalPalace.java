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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.watchers.WatcherImpl;

/**
 *
 * @author LevelX2
 */
public class OpalPalace extends CardImpl<OpalPalace> {

    public OpalPalace(UUID ownerId) {
        super(ownerId, 310, "Opal Palace", Rarity.COMMON, new CardType[]{CardType.LAND}, "");
        this.expansionSetCode = "C13";

        // {tap}: Add {1} to your mana pool.
        this.addAbility(new ColorlessManaAbility());
        // {1}, {tap}: Add to your mana pool one mana of any color in your commander's color identity. If you spend this mana to cast your commander, it enters the battlefield with a number of +1/+1 counters on it equal to the number of times it's been cast from the command zone this game.
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, new OpalPalaceManaEffect(), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        ability = new SimpleStaticAbility(Zone.ALL, new OpalPalaceEntersBattlefieldEffect());
        ability.setRuleVisible(false);
        this.addAbility(ability);
        
        this.addWatcher(new OpalPalaceWatcher());
    }

    public OpalPalace(final OpalPalace card) {
        super(card);
    }

    @Override
    public OpalPalace copy() {
        return new OpalPalace(this);
    }
}

class OpalPalaceManaEffect extends ManaEffect<OpalPalaceManaEffect> {

    public OpalPalaceManaEffect() {
        super();
        this.staticText = "Add to your mana pool one mana of any color in your commander's color identity. If you spend this mana to cast your commander, it enters the battlefield with a number of +1/+1 counters on it equal to the number of times it's been cast from the command zone this game";
    }

    public OpalPalaceManaEffect(final OpalPalaceManaEffect effect) {
        super(effect);
    }

    @Override
    public OpalPalaceManaEffect copy() {
        return new OpalPalaceManaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card commander = game.getCard(controller.getCommanderId());
            if (commander != null) {
                Mana commanderMana = commander.getManaCost().getMana();
                Choice choice = new ChoiceImpl(true);
                choice.setMessage("Pick a mana color");
                if (commanderMana.getBlack() > 0) {
                    choice.getChoices().add("Black");
                }
                if (commanderMana.getRed() > 0) {
                    choice.getChoices().add("Red");
                }
                if (commanderMana.getBlue() > 0) {
                    choice.getChoices().add("Blue");
                }
                if (commanderMana.getGreen() > 0) {
                    choice.getChoices().add("Green");
                }
                if (commanderMana.getWhite() > 0) {
                    choice.getChoices().add("White");
                }
                if (choice.getChoices().size() > 0) {
                    if (choice.getChoices().size() == 1) {
                        choice.setChoice(choice.getChoices().iterator().next());
                    } else {
                        controller.choose(outcome, choice, game);
                    }
                    if (choice.getChoice().equals("Black")) {
                        controller.getManaPool().addMana(Mana.BlackMana, game, source);
                    } else if (choice.getChoice().equals("Blue")) {
                        controller.getManaPool().addMana(Mana.BlueMana, game, source);
                    } else if (choice.getChoice().equals("Red")) {
                        controller.getManaPool().addMana(Mana.RedMana, game, source);
                    } else if (choice.getChoice().equals("Green")) {
                        controller.getManaPool().addMana(Mana.GreenMana, game, source);
                    } else if (choice.getChoice().equals("White")) {
                        controller.getManaPool().addMana(Mana.WhiteMana, game, source);
                    }
                }
                return true;
            }
        }
        return false;
    }
}

class OpalPalaceWatcher extends WatcherImpl<OpalPalaceWatcher> {

    public List<UUID> commanderId = new ArrayList<UUID>();

    public OpalPalaceWatcher() {
        super("ManaPaidFromOpalPalaceWatcher", WatcherScope.CARD);
    }

    public OpalPalaceWatcher(final OpalPalaceWatcher watcher) {
        super(watcher);
    }

    @Override
    public OpalPalaceWatcher copy() {
        return new OpalPalaceWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.MANA_PAYED) {
            if (event.getSourceId().equals(this.getSourceId())) {
                Spell spell = game.getStack().getSpell(event.getTargetId());
                if (spell != null) {
                    Card card = spell.getCard();
                    if (card != null) {
                        for (UUID playerId :game.getPlayerList()) {
                            Player player = game.getPlayer(playerId);
                            if (player != null) {
                                if (player.getCommanderId() != null && player.getCommanderId().equals(card.getId())) {
                                    commanderId.add(card.getId());
                                    break;
                                }
                            }                            
                        }
                    }                   
                }
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        commanderId.clear();
    }
}

class OpalPalaceEntersBattlefieldEffect extends ReplacementEffectImpl<OpalPalaceEntersBattlefieldEffect> {

    public OpalPalaceEntersBattlefieldEffect() {
        super(Duration.EndOfGame, Outcome.BoostCreature, false);
        staticText = "If you spend this mana to cast your commander, it enters the battlefield with a number of +1/+1 counters on it equal to the number of times it's been cast from the command zone this game";
    }

    public OpalPalaceEntersBattlefieldEffect(OpalPalaceEntersBattlefieldEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == EventType.ENTERS_THE_BATTLEFIELD) {
            OpalPalaceWatcher watcher = (OpalPalaceWatcher) game.getState().getWatchers().get("ManaPaidFromOpalPalaceWatcher", source.getSourceId());
            if (watcher != null) {
                return watcher.commanderId.contains(event.getTargetId());
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
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null) {
            Integer castCount = (Integer)game.getState().getValue(permanent.getId() + "_castCount");
            if (castCount != null && castCount.intValue() > 1) {
                permanent.addCounters(CounterType.P1P1.createInstance(castCount-1), game);
            }
        }
        return false;
    }

    @Override
    public OpalPalaceEntersBattlefieldEffect copy() {
        return new OpalPalaceEntersBattlefieldEffect(this);
    }

}
