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
package mage.sets.mirrodin;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetCardInASingleGraveyard;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 *
 * @author emerald000
 */
public class SpellweaverHelix extends CardImpl {
    
    private static final FilterCard filter = new FilterCard("sorcery cards from a single graveyard");
    static {
        filter.add(new CardTypePredicate(CardType.SORCERY));
    }

    public SpellweaverHelix(UUID ownerId) {
        super(ownerId, 247, "Spellweaver Helix", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.expansionSetCode = "MRD";

        // Imprint - When Spellweaver Helix enters the battlefield, you may exile two target sorcery cards from a single graveyard.
        Ability ability = new EntersBattlefieldTriggeredAbility(new SpellweaverHelixImprintEffect(), true, "Imprint &mdash; ");
        ability.addTarget(new TargetCardInASingleGraveyard(2, 2, filter));
        this.addAbility(ability);
        
        // Whenever a player casts a card, if it has the same name as one of the cards exiled with Spellweaver Helix, you may copy the other. If you do, you may cast the copy without paying its mana cost.
        this.addAbility(new SpellweaverHelixTriggeredAbility());
    }

    public SpellweaverHelix(final SpellweaverHelix card) {
        super(card);
    }

    @java.lang.Override
    public SpellweaverHelix copy() {
        return new SpellweaverHelix(this);
    }
}

class SpellweaverHelixImprintEffect extends OneShotEffect {
    
    SpellweaverHelixImprintEffect() {
        super(Outcome.Exile);
        this.staticText = "you may exile two target sorcery cards from a single graveyard";
    }
    
    SpellweaverHelixImprintEffect(final SpellweaverHelixImprintEffect effect) {
        super(effect);
    }
    
    @java.lang.Override
    public SpellweaverHelixImprintEffect copy() {
        return new SpellweaverHelixImprintEffect(this);
    }
    
    @java.lang.Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent sourcePermanent = game.getPermanent(source.getSourceId());
            for (UUID targetId : this.getTargetPointer().getTargets(game, source)) {
                Card card = game.getCard(targetId);
                if (card != null) {
                    controller.moveCardsToExile(card, source, game, true, CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter()), source.getSourceObject(game).getIdName());
                    if (sourcePermanent != null) {
                        sourcePermanent.imprint(targetId, game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}

class SpellweaverHelixTriggeredAbility extends TriggeredAbilityImpl {
    
    SpellweaverHelixTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SpellweaverHelixCastEffect(), false);
    }
    
    SpellweaverHelixTriggeredAbility(final SpellweaverHelixTriggeredAbility ability) {
        super(ability);
    }
    
    @java.lang.Override
    public SpellweaverHelixTriggeredAbility copy() {
        return new SpellweaverHelixTriggeredAbility(this);
    }
    
    @java.lang.Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @java.lang.Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = game.getStack().getSpell(event.getTargetId());
        if (spell != null && spell.getCard() != null && !spell.getCard().isCopy()) {
            for (Effect effect : this.getEffects()) {
                effect.setTargetPointer(new FixedTarget(spell.getId()));
            }
            return true;
        }
        return false;
    }

    @java.lang.Override
    public boolean checkInterveningIfClause(Game game) {
        Spell spell = game.getStack().getSpell(this.getEffects().get(0).getTargetPointer().getFirst(game, this));
        if (spell != null) {
            String spellName = spell.getName();
            Permanent sourcePermanent = game.getPermanent(this.getSourceId());
            if (sourcePermanent != null) {
                for (UUID imprintId : sourcePermanent.getImprinted()) {
                    Card card = game.getCard(imprintId);
                    if (card != null && card.getName().equals(spellName)) {
                        ((SpellweaverHelixCastEffect) this.getEffects().get(0)).setSpellName(spellName);
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    @java.lang.Override
    public String getRule() {
        return "Whenever a player casts a card, if it has the same name as one of the cards exiled with Spellweaver Helix, you may copy the other. If you do, you may cast the copy without paying its mana cost.";
    }
}

class SpellweaverHelixCastEffect extends OneShotEffect {
    
    private String spellName = "";
    
    SpellweaverHelixCastEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may copy the other. If you do, you may cast the copy without paying its mana cost";
    }
    
    SpellweaverHelixCastEffect(final SpellweaverHelixCastEffect effect) {
        super(effect);
        this.spellName = effect.spellName;
    }
    
    @java.lang.Override
    public SpellweaverHelixCastEffect copy() {
        return new SpellweaverHelixCastEffect(this);
    }
    
    public void setSpellName(String spellName) {
        this.spellName = spellName;
    }
    
    @java.lang.Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
            if (sourcePermanent != null) {
                boolean foundSpellWithSameName = false;
                for (UUID imprintId : sourcePermanent.getImprinted()) {
                    Card card = game.getCard(imprintId);
                    if (card != null) {
                        if (!foundSpellWithSameName && card.getName().equals(spellName)) {
                            foundSpellWithSameName = true;
                        }
                        else {
                            if (controller.chooseUse(Outcome.Copy, "Copy " + card.getIdName(), source, game)) {
                                Card copy = game.copyCard(card, source, source.getControllerId());
                                if (controller.chooseUse(Outcome.PlayForFree, "Cast " + copy.getIdName() + " without paying its mana cost?", source, game)) {
                                    controller.cast(copy.getSpellAbility(), game, true);
                                }
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
