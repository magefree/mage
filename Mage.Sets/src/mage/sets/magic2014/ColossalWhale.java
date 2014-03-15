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
package mage.sets.magic2014;

import java.util.LinkedList;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.IslandwalkAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class ColossalWhale extends CardImpl<ColossalWhale> {

    public ColossalWhale(UUID ownerId) {
        super(ownerId, 48, "Colossal Whale", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{5}{U}{U}");
        this.expansionSetCode = "M14";
        this.subtype.add("Whale");

        this.color.setBlue(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Islandwalk
        this.addAbility(new IslandwalkAbility());
        // Whenever Colossal Whale attacks, you may exile target creature defending player controls until Colossal Whale leaves the battlefield.
        this.addAbility(new ColossalWhaleAbility());
        // Implemented as triggered effect that doesn't uses the stack (implementation with watcher does not work correctly because if the returned creature
        // has a DiesTriggeredAll ability it triggers for the dying Banish Priest, what shouldn't happen)
        this.addAbility(new ColossalWhaleReturnExiledAbility());

    }

    public ColossalWhale(final ColossalWhale card) {
        super(card);
    }

    @Override
    public ColossalWhale copy() {
        return new ColossalWhale(this);
    }
}

class ColossalWhaleAbility extends TriggeredAbilityImpl<ColossalWhaleAbility> {

    public ColossalWhaleAbility() {
        super(Zone.BATTLEFIELD, null);
        this.addEffect(new ColossalWhaleExileEffect());
    }

    public ColossalWhaleAbility(final ColossalWhaleAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ATTACKER_DECLARED && event.getSourceId().equals(this.getSourceId())) {
            FilterCreaturePermanent filter = new FilterCreaturePermanent("creature defending player controls");
            UUID defenderId = game.getCombat().getDefenderId(sourceId);
            filter.add(new ControllerIdPredicate(defenderId));

            this.getTargets().clear();
            TargetCreaturePermanent target = new TargetCreaturePermanent(filter);
            target.setRequired(true);
            this.addTarget(target);
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return new StringBuilder("Whenever {this} attacks, ").append(super.getRule()).toString();
    }

    @Override
    public ColossalWhaleAbility copy() {
        return new ColossalWhaleAbility(this);
    }
}

class ColossalWhaleExileEffect extends OneShotEffect<ColossalWhaleExileEffect> {

    public ColossalWhaleExileEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may exile target creature defending player controls until {this} leaves the battlefield";
    }

    public ColossalWhaleExileEffect(final ColossalWhaleExileEffect effect) {
        super(effect);
    }

    @Override
    public ColossalWhaleExileEffect copy() {
        return new ColossalWhaleExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        // If Whale leaves the battlefield before its triggered ability resolves,
        // the target creature won't be exiled.
        if (permanent != null) {
            return new ExileTargetEffect(source.getSourceId(), permanent.getName()).apply(game, source);
        }
        return false;
    }
}

/**
 * Returns the exiled card as Banisher Priest leaves battlefield
 * Uses no stack
 * @author LevelX2
 */

class ColossalWhaleReturnExiledAbility extends TriggeredAbilityImpl<ColossalWhaleReturnExiledAbility> {

    public ColossalWhaleReturnExiledAbility() {
        super(Zone.BATTLEFIELD, new ReturnExiledCreatureColossalWhaleEffect());
        this.usesStack = false;
        this.setRuleVisible(false);
    }

    public ColossalWhaleReturnExiledAbility(final ColossalWhaleReturnExiledAbility ability) {
        super(ability);
    }

    @Override
    public ColossalWhaleReturnExiledAbility copy() {
        return new ColossalWhaleReturnExiledAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE && event.getTargetId().equals(this.getSourceId())) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.getFromZone() == Zone.BATTLEFIELD) {
                return true;
            }
        }
        return false;
    }
}

class ReturnExiledCreatureColossalWhaleEffect extends OneShotEffect<ReturnExiledCreatureColossalWhaleEffect> {

    public ReturnExiledCreatureColossalWhaleEffect() {
        super(Outcome.Benefit);
        this.staticText = "Return exiled creatures";
    }

    public ReturnExiledCreatureColossalWhaleEffect(final ReturnExiledCreatureColossalWhaleEffect effect) {
        super(effect);
    }

    @Override
    public ReturnExiledCreatureColossalWhaleEffect copy() {
        return new ReturnExiledCreatureColossalWhaleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        ExileZone exile = game.getExile().getExileZone(source.getSourceId());
        Card sourceCard = game.getCard(source.getSourceId());
        if (exile != null && sourceCard != null) {
            LinkedList<UUID> cards = new LinkedList<>(exile);
            for (UUID cardId : cards) {
                Card card = game.getCard(cardId);
                card.moveToZone(Zone.BATTLEFIELD, source.getSourceId(), game, false);
                game.informPlayers(new StringBuilder(sourceCard.getName()).append(": ").append(card.getName()).append(" returns to battlefield from exile").toString());
            }
            exile.clear();
            return true;
        }
        return false;
    }
}
