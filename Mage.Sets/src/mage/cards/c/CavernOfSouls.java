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
package mage.cards.c;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.ConditionalMana;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.CreatureCastManaCondition;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.watchers.Watcher;

/**
 *
 * @author noxx
 */
public class CavernOfSouls extends CardImpl {

    public CavernOfSouls(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // As Cavern of Souls enters the battlefield, choose a creature type.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.BoostCreature)));

        // {T}: Add {C} to your mana pool.
        this.addAbility(new ColorlessManaAbility());

        // {T}: Add one mana of any color to your mana pool. Spend this mana only to cast a creature spell of the chosen type, and that spell can't be countered.
        Ability ability = new ConditionalAnyColorManaAbility(new TapSourceCost(), 1, new CavernOfSoulsManaBuilder(), true);
        this.addAbility(ability, new CavernOfSoulsWatcher(ability.getOriginalId()));
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new CavernOfSoulsCantCounterEffect()));
    }

    public CavernOfSouls(final CavernOfSouls card) {
        super(card);
    }

    @Override
    public CavernOfSouls copy() {
        return new CavernOfSouls(this);
    }
}

class CavernOfSoulsManaBuilder extends ConditionalManaBuilder {

    String creatureType;

    @Override
    public ConditionalManaBuilder setMana(Mana mana, Ability source, Game game) {
        Object value = game.getState().getValue(source.getSourceId() + "_type");
        if (value != null && value instanceof String) {
            creatureType = (String) value;
        }
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null && sourceObject != null) {
            game.informPlayers(controller.getLogName() + " produces " + mana.toString() + " with " + sourceObject.getLogName()
                    + " (can only be spend to cast for creatures of type " + creatureType + " and that spell can't be countered)");
        }
        return super.setMana(mana, source, game);
    }

    @Override
    public ConditionalMana build(Object... options) {
        return new CavernOfSoulsConditionalMana(this.mana, creatureType);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast a creature spell of the chosen type, and that spell can't be countered";
    }
}

class CavernOfSoulsConditionalMana extends ConditionalMana {

    public CavernOfSoulsConditionalMana(Mana mana, String creatureType) {
        super(mana);
        staticText = "Spend this mana only to cast a creature spell of the chosen type, and that spell can't be countered";
        addCondition(new CavernOfSoulsManaCondition(creatureType));
    }
}

class CavernOfSoulsManaCondition extends CreatureCastManaCondition {

    String creatureType;

    CavernOfSoulsManaCondition(String creatureType) {
        this.creatureType = creatureType;
    }

    @Override
    public boolean apply(Game game, Ability source, UUID originalId, Cost costToPay) {
        // check: ... to cast a creature spell
        if (super.apply(game, source)) {
            // check: ... of the chosen type
            MageObject object = game.getObject(source.getSourceId());
            if (creatureType != null && object.hasSubtype(creatureType, game)) {
                return true;
            }
        }
        return false;
    }
}

class CavernOfSoulsWatcher extends Watcher {

    private List<UUID> spells = new ArrayList<>();
    private final String originalId;

    public CavernOfSoulsWatcher(UUID originalId) {
        super("ManaPaidFromCavernOfSoulsWatcher", WatcherScope.CARD);
        this.originalId = originalId.toString();
    }

    public CavernOfSoulsWatcher(final CavernOfSoulsWatcher watcher) {
        super(watcher);
        this.spells.addAll(watcher.spells);
        this.originalId = watcher.originalId;
    }

    @Override
    public CavernOfSoulsWatcher copy() {
        return new CavernOfSoulsWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.MANA_PAYED) {
            if (event.getData() != null && event.getData().equals(originalId)) {
                spells.add(event.getTargetId());
            }
        }
    }

    public boolean spellCantBeCountered(UUID spellId) {
        return spells.contains(spellId);
    }

    @Override
    public void reset() {
        super.reset();
        spells.clear();
    }
}

class CavernOfSoulsCantCounterEffect extends ContinuousRuleModifyingEffectImpl {

    public CavernOfSoulsCantCounterEffect() {
        super(Duration.EndOfGame, Outcome.Benefit);
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
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (sourceObject != null) {
            return "This spell can't be countered because a colored mana from " + sourceObject.getName() + " was spent to cast it.";
        }
        return null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        CavernOfSoulsWatcher watcher = (CavernOfSoulsWatcher) game.getState().getWatchers().get("ManaPaidFromCavernOfSoulsWatcher", source.getSourceId());
        Spell spell = game.getStack().getSpell(event.getTargetId());
        return spell != null && watcher != null && watcher.spellCantBeCountered(spell.getId());
    }
}
