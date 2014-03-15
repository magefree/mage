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
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth using LevelX2 tech
 */
public class BanisherPriest extends CardImpl<BanisherPriest> {

    private final static FilterCreaturePermanent filter = new FilterCreaturePermanent("creature an opponent controls");
    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public BanisherPriest(UUID ownerId) {
        super(ownerId, 7, "Banisher Priest", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");
        this.expansionSetCode = "M14";
        this.subtype.add("Human");
        this.subtype.add("Cleric");

        this.color.setWhite(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Banisher Priest enters the battlefield, exile target creature an opponent controls until Banisher Priest leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BanisherPriestExileEffect());
        ability.addTarget(new TargetCreaturePermanent(filter, true));
        this.addAbility(ability);
        // Implemented as triggered effect that doesn't uses the stack (implementation with watcher does not work correctly because if the returned creature
        // has a DiesTriggeredAll ability it triggers for the dying Banish Priest, what shouldn't happen)
        this.addAbility(new BanisherPriestReturnExiledAbility());

    }

    public BanisherPriest(final BanisherPriest card) {
        super(card);
    }

    @Override
    public BanisherPriest copy() {
        return new BanisherPriest(this);
    }
}

class BanisherPriestExileEffect extends OneShotEffect<BanisherPriestExileEffect> {

    public BanisherPriestExileEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile target creature an opponent controls until {this} leaves the battlefield. <i>(That creature returns under its owner's control.)</i>";
    }

    public BanisherPriestExileEffect(final BanisherPriestExileEffect effect) {
        super(effect);
    }

    @Override
    public BanisherPriestExileEffect copy() {
        return new BanisherPriestExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        // If Banisher Priest leaves the battlefield before its triggered ability resolves,
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

class BanisherPriestReturnExiledAbility extends TriggeredAbilityImpl<BanisherPriestReturnExiledAbility> {

    public BanisherPriestReturnExiledAbility() {
        super(Zone.BATTLEFIELD, new ReturnExiledCreatureEffect());
        this.usesStack = false;
        this.setRuleVisible(false);
    }

    public BanisherPriestReturnExiledAbility(final BanisherPriestReturnExiledAbility ability) {
        super(ability);
    }

    @Override
    public BanisherPriestReturnExiledAbility copy() {
        return new BanisherPriestReturnExiledAbility(this);
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

class ReturnExiledCreatureEffect extends OneShotEffect<ReturnExiledCreatureEffect> {

    public ReturnExiledCreatureEffect() {
        super(Outcome.Benefit);
        this.staticText = "Return exiled creature";
    }

    public ReturnExiledCreatureEffect(final ReturnExiledCreatureEffect effect) {
        super(effect);
    }

    @Override
    public ReturnExiledCreatureEffect copy() {
        return new ReturnExiledCreatureEffect(this);
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
