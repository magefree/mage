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
package mage.sets.alarareborn;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.CascadeAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.watchers.WatcherImpl;

/**
 *
 * @author jeffwadsworth
 */
public class MaelstromNexus extends CardImpl<MaelstromNexus> {

    public MaelstromNexus(UUID ownerId) {
        super(ownerId, 130, "Maelstrom Nexus", Rarity.MYTHIC, new CardType[]{CardType.ENCHANTMENT}, "{W}{U}{B}{R}{G}");
        this.expansionSetCode = "ARB";

        this.color.setRed(true);
        this.color.setBlue(true);
        this.color.setGreen(true);
        this.color.setBlack(true);
        this.color.setWhite(true);

        // The first spell you cast each turn has cascade.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MaelstromNexusEffect(new CascadeAbility(), Duration.WhileOnStack, "The first spell you cast each turn has cascade", true)));
        this.addWatcher(new CastSpellThisTurnWatcher());

    }

    public MaelstromNexus(final MaelstromNexus card) {
        super(card);
    }

    @Override
    public MaelstromNexus copy() {
        return new MaelstromNexus(this);
    }
}

class CastSpellThisTurnWatcher extends WatcherImpl<CastSpellThisTurnWatcher> {

    int spellCount = 0;

    public CastSpellThisTurnWatcher() {
        super("CastSpellThisTurn", WatcherScope.CARD);
    }

    public CastSpellThisTurnWatcher(final CastSpellThisTurnWatcher watcher) {
        super(watcher);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST && event.getPlayerId() == controllerId) {
            Spell spell = (Spell) game.getObject(event.getTargetId());
            if (this.getSourceId().equals(spell.getSourceId())) {
                spellCount++;
                if (spellCount == 1) {
                    condition = true;
                }
            }
        }
    }

    @Override
    public CastSpellThisTurnWatcher copy() {
        return new CastSpellThisTurnWatcher(this);
    }

    @Override
    public void reset() {
        super.reset();
        spellCount = 0;
    }
}

class MaelstromNexusEffect extends ContinuousEffectImpl<MaelstromNexusEffect> {

    protected Ability ability;
    // shall a card gain the ability (otherwise permanent)
    private boolean onCard;

    public MaelstromNexusEffect(Ability ability, Duration duration) {
        this(ability, duration, null);
    }

    public MaelstromNexusEffect(Ability ability, Duration duration, String rule) {
        this(ability, duration, rule, false);
    }

    public MaelstromNexusEffect(Ability ability, Duration duration, String rule, boolean onCard) {
        super(duration, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA,
                ability.getEffects().size() > 0 ? ability.getEffects().get(0).getOutcome() : Outcome.AddAbility);
        this.ability = ability;
        staticText = rule;
        this.onCard = onCard;
    }

    public MaelstromNexusEffect(final MaelstromNexusEffect effect) {
        super(effect);
        this.ability = effect.ability.copy();
        this.onCard = effect.onCard;
    }

    @Override
    public MaelstromNexusEffect copy() {
        return new MaelstromNexusEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        targetPointer.init(game, source);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        CastSpellThisTurnWatcher watcher = (CastSpellThisTurnWatcher) game.getState().getWatchers().get("CastSpellThisTurn", source.getSourceId());
        StackObject spellObject = game.getStack().getFirst();
        Spell spell = game.getStack().getSpell(spellObject.getSourceId());
        if (spell == null) {
            return false;
        }
        if (watcher.conditionMet()
                && spell.getControllerId() == source.getControllerId()) {
            if (onCard) {
                Card card = game.getCard(spell.getSourceId());
                if (card != null) {
                    card.addAbility(ability);
                }
            }
        }
        return false;
    }
}