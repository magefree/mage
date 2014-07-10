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
package mage.sets.magic2015;

import java.util.LinkedList;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.continious.GainAbilityAllEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class ConstrictingSliver extends CardImpl {

    private static final FilterCreaturePermanent filterTarget = new FilterCreaturePermanent("creature an opponent controls");

    static {
        filterTarget.add(new ControllerPredicate(TargetController.OPPONENT));
    }


    public ConstrictingSliver(UUID ownerId) {
        super(ownerId, 7, "Constricting Sliver", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{5}{W}");
        this.expansionSetCode = "M15";
        this.subtype.add("Sliver");

        this.color.setWhite(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Sliver creatures you control have "When this creature enters the battlefield, you may exile target creature an opponent controls 
        // until this creature leaves the battlefield."
        Ability ability = new EntersBattlefieldTriggeredAbility(new ConstrictingSliverExileEffect(), true);
        ability.addTarget(new TargetCreaturePermanent(filterTarget));
        ability.addEffect(new ConstrictingSliverAddDelayedReturnEffect());
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new GainAbilityAllEffect(ability,
                Duration.WhileOnBattlefield, new FilterControlledCreaturePermanent("Sliver","Sliver creatures"),
                "Sliver creatures you control have \"When this creature enters the battlefield, you may exile target creature an opponent controls until this creature leaves the battlefield.\"")));

    }

    public ConstrictingSliver(final ConstrictingSliver card) {
        super(card);
    }

    @Override
    public ConstrictingSliver copy() {
        return new ConstrictingSliver(this);
    }
}

class ConstrictingSliverExileEffect extends OneShotEffect {

    public ConstrictingSliverExileEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may exile target creature an opponent controls until this creature leaves the battlefield";
    }

    public ConstrictingSliverExileEffect(final ConstrictingSliverExileEffect effect) {
        super(effect);
    }

    @Override
    public ConstrictingSliverExileEffect copy() {
        return new ConstrictingSliverExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        // If the creature leaves the battlefield before its triggered ability resolves,
        // the target creature won't be exiled.
        if (permanent != null) {
            return new ExileTargetEffect(source.getSourceId(), permanent.getLogName()).apply(game, source);
        }
        return false;
    }
}

class ConstrictingSliverAddDelayedReturnEffect extends OneShotEffect {

    public ConstrictingSliverAddDelayedReturnEffect() {
        super(Outcome.Benefit);
        this.staticText = "";
    }

    public ConstrictingSliverAddDelayedReturnEffect(final ConstrictingSliverAddDelayedReturnEffect effect) {
        super(effect);
    }

    @Override
    public ConstrictingSliverAddDelayedReturnEffect copy() {
        return new ConstrictingSliverAddDelayedReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        DelayedTriggeredAbility delayedAbility = new ConstrictingSliverReturnExiledCreatureAbility();
        delayedAbility.setSourceId(source.getSourceId());
        delayedAbility.setControllerId(source.getControllerId());
        game.addDelayedTriggeredAbility(delayedAbility);
        return true;
    }
}


/**
 * Returns the exiled card as creature leaves battlefield
 * Uses no stack
 * @author LevelX2
 */

class ConstrictingSliverReturnExiledCreatureAbility extends DelayedTriggeredAbility {

    public ConstrictingSliverReturnExiledCreatureAbility() {
        super(new ConstrictingSliverReturnExiledCreatureEffect(), Duration.OneUse);
        this.usesStack = false;
        this.setRuleVisible(false);
    }

    public ConstrictingSliverReturnExiledCreatureAbility(final ConstrictingSliverReturnExiledCreatureAbility ability) {
        super(ability);
    }

    @Override
    public ConstrictingSliverReturnExiledCreatureAbility copy() {
        return new ConstrictingSliverReturnExiledCreatureAbility(this);
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

class ConstrictingSliverReturnExiledCreatureEffect extends OneShotEffect {

    public ConstrictingSliverReturnExiledCreatureEffect() {
        super(Outcome.Benefit);
        this.staticText = "Return exiled creatures";
    }

    public ConstrictingSliverReturnExiledCreatureEffect(final ConstrictingSliverReturnExiledCreatureEffect effect) {
        super(effect);
    }

    @Override
    public ConstrictingSliverReturnExiledCreatureEffect copy() {
        return new ConstrictingSliverReturnExiledCreatureEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
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

        }
        return false;
    }
}
