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
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.WatcherImpl;

/**
 *
 * @author jeffwadsworth using LevelX2 tech
 */
public class BanisherPriest extends CardImpl<BanisherPriest> {

    private UUID exileId = UUID.randomUUID();

    public BanisherPriest(UUID ownerId) {
        super(ownerId, 7, "Banisher Priest", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");
        this.expansionSetCode = "M14";
        this.subtype.add("Human");
        this.subtype.add("Cleric");

        this.color.setWhite(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Banisher Priest enters the battlefield, exile target creature an opponent controls until Banisher Priest leaves the battlefield.
        this.addAbility(new BanisherPriestTriggeredAbility(exileId));
        this.addWatcher(new BanisherPriestWatcher(exileId));

    }

    public BanisherPriest(final BanisherPriest card) {
        super(card);
    }

    @Override
    public BanisherPriest copy() {
        return new BanisherPriest(this);
    }
}

class BanisherPriestTriggeredAbility extends TriggeredAbilityImpl<BanisherPriestTriggeredAbility> {

    public BanisherPriestTriggeredAbility(UUID exileId) {
        super(Zone.BATTLEFIELD, null, false);
        this.addEffect(new ExileTargetEffect(exileId, "Banisher Priest"));
    }

    public BanisherPriestTriggeredAbility(final BanisherPriestTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD && event.getTargetId().equals(this.getSourceId())) {
            FilterCreaturePermanent filter = new FilterCreaturePermanent("creature an opponent controls");
            filter.add(Predicates.not(new ControllerIdPredicate(controllerId)));
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
        return "Whenever {this} enters the battlefield, exile target creature an opponent controls until Banisher Priest leaves the battlefield.";
    }

    @Override
    public BanisherPriestTriggeredAbility copy() {
        return new BanisherPriestTriggeredAbility(this);
    }
}

class BanisherPriestWatcher extends WatcherImpl<BanisherPriestWatcher> {

    UUID exileId;

    BanisherPriestWatcher(UUID exileId) {
        super("BattlefieldLeft", WatcherScope.CARD);
        this.exileId = exileId;
    }

    BanisherPriestWatcher(final BanisherPriestWatcher watcher) {
        super(watcher);
        this.exileId = watcher.exileId;
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE && event.getTargetId().equals(sourceId)) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.getFromZone() == Zone.BATTLEFIELD) {
                ExileZone exile = game.getExile().getExileZone(exileId);
                if (exile != null) {
                    LinkedList<UUID> cards = new LinkedList<UUID>(exile);
                    for (UUID cardId : cards) {
                        Card card = game.getCard(cardId);
                        card.moveToZone(Zone.BATTLEFIELD, this.getSourceId(), game, false);
                    }
                    exile.clear();
                }
            }
        }
    }

    @Override
    public void reset() {
        //don't reset condition each turn - only when this leaves the battlefield
    }

    @Override
    public BanisherPriestWatcher copy() {
        return new BanisherPriestWatcher(this);
    }
}
