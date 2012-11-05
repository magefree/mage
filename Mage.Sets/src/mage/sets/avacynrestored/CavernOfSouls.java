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
package mage.sets.avacynrestored;

import mage.ConditionalMana;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.CreatureCastManaCondition;
import mage.cards.CardImpl;
import mage.cards.repository.CardRepository;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.watchers.WatcherImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author noxx
 */
public class CavernOfSouls extends CardImpl<CavernOfSouls> {

    private static final String ruleText = "choose a creature type";

    public CavernOfSouls(UUID ownerId) {
        super(ownerId, 226, "Cavern of Souls", Rarity.RARE, new CardType[]{CardType.LAND}, "");
        this.expansionSetCode = "AVR";

        // As Cavern of Souls enters the battlefield, choose a creature type.
        this.addAbility(new AsEntersBattlefieldAbility(new CavernOfSoulsEffect(), ruleText));

        // {tap}: Add {1} to your mana pool.
        this.addAbility(new ColorlessManaAbility());

        // {tap}: Add one mana of any color to your mana pool. Spend this mana only to cast a creature spell of the chosen type, and that spell can't be countered.
        this.addAbility(new ConditionalAnyColorManaAbility(1, new CavernOfSoulsManaBuilder()));
        this.addWatcher(new CavernOfSoulsWatcher());
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new CavernOfSoulsCantCounterEffect()));
    }

    public CavernOfSouls(final CavernOfSouls card) {
        super(card);
    }

    @Override
    public CavernOfSouls copy() {
        return new CavernOfSouls(this);
    }
}

class CavernOfSoulsEffect extends OneShotEffect<CavernOfSoulsEffect> {

    public CavernOfSoulsEffect() {
        super(Constants.Outcome.Benefit);
        staticText = "As {this} enters the battlefield, choose a creature type";
    }

    public CavernOfSoulsEffect(final CavernOfSoulsEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (player != null && permanent != null) {
            Choice typeChoice = new ChoiceImpl(true);
            typeChoice.setMessage("Choose creature type");
            typeChoice.setChoices(CardRepository.instance.getCreatureTypes());
            while (!player.choose(Constants.Outcome.Benefit, typeChoice, game)) {
                game.debugMessage("player canceled choosing type. retrying.");
            }
            game.informPlayers(permanent.getName() + ": " + player.getName() + " has chosen " + typeChoice.getChoice());
            game.getState().setValue(permanent.getId() + "_type", typeChoice.getChoice());
            permanent.addInfo("chosen type", "<i>Chosen type: " + typeChoice.getChoice() + "</i>");
        }
        return false;
    }

    @Override
    public CavernOfSoulsEffect copy() {
        return new CavernOfSoulsEffect(this);
    }

}

class CavernOfSoulsManaBuilder extends ConditionalManaBuilder {
    @Override
    public ConditionalMana build(Object... options) {
        return new CavernOfSoulsConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast a creature spell of the chosen type, and that spell can't be countered";
    }
}

class CavernOfSoulsConditionalMana extends ConditionalMana {

    public CavernOfSoulsConditionalMana(Mana mana) {
        super(mana);
        staticText = "Spend this mana only to cast a creature spell of the chosen type, and that spell can't be countered";
        addCondition(new CavernOfSoulsManaCondition());
    }
}

class CavernOfSoulsManaCondition extends CreatureCastManaCondition {

    @Override
    public boolean apply(Game game, Ability source, UUID manaProducer) {
        // check: ... to cast a creature spell
        if (super.apply(game, source)) {
            // check: ... of the chosen type
            Object value = game.getState().getValue(manaProducer + "_type");
            if (value != null && value instanceof String) {
                MageObject object = game.getObject(source.getSourceId());
                if (object.getSubtype().contains(value)) {
                    return true;
                }
            }
        }
        return false;
    }
}

class CavernOfSoulsWatcher extends WatcherImpl<CavernOfSoulsWatcher> {

    public List<UUID> spells = new ArrayList<UUID>();

    public CavernOfSoulsWatcher() {
        super("ManaPaidFromCavernOfSoulsWatcher", Constants.WatcherScope.GAME);
    }

    public CavernOfSoulsWatcher(final CavernOfSoulsWatcher watcher) {
        super(watcher);
    }

    @Override
    public CavernOfSoulsWatcher copy() {
        return new CavernOfSoulsWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.MANA_PAYED) {
            if (event.getSourceId().equals(this.sourceId)) {
                spells.add(event.getTargetId());
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        spells.clear();
    }

}

class CavernOfSoulsCantCounterEffect extends ReplacementEffectImpl<CavernOfSoulsCantCounterEffect> {

    public CavernOfSoulsCantCounterEffect() {
        super(Constants.Duration.WhileOnBattlefield, Constants.Outcome.Benefit);
        //staticText = "that spell can't be countered";
        staticText = null;
    }

    public CavernOfSoulsCantCounterEffect(final CavernOfSoulsCantCounterEffect effect) {
        super(effect);
    }

    @Override
    public CavernOfSoulsCantCounterEffect copy() {
        return new CavernOfSoulsCantCounterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.COUNTER) {
            CavernOfSoulsWatcher watcher = (CavernOfSoulsWatcher) game.getState().getWatchers().get("ManaPaidFromCavernOfSoulsWatcher");
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null && watcher.spells.contains(spell.getId())) {
                return true;
            }
        }
        return false;
    }

}