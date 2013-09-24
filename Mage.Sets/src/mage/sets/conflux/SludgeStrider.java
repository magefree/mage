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
package mage.sets.conflux;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterArtifactCard;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author jeffwadsworth
 */
public class SludgeStrider extends CardImpl<SludgeStrider> {

    public SludgeStrider(UUID ownerId) {
        super(ownerId, 126, "Sludge Strider", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{W}{U}{B}");
        this.expansionSetCode = "CON";
        this.subtype.add("Insect");

        this.color.setBlue(true);
        this.color.setBlack(true);
        this.color.setWhite(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever another artifact enters the battlefield under your control or another artifact you control leaves the battlefield, you may pay {1}. If you do, target player loses 1 life and you gain 1 life.
        Ability ability = new SludgeStriderTriggeredAbility();
        ability.addTarget(new TargetPlayer(true));
        this.addAbility(ability);

    }

    public SludgeStrider(final SludgeStrider card) {
        super(card);
    }

    @Override
    public SludgeStrider copy() {
        return new SludgeStrider(this);
    }
}

class SludgeStriderTriggeredAbility extends TriggeredAbilityImpl<SludgeStriderTriggeredAbility> {

    private static final FilterArtifactCard filter = new FilterArtifactCard("another artifact under your control");
    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(new AnotherPredicate());
    }

    public SludgeStriderTriggeredAbility() {
        // setting optional = false because DoIfCostPaid already asks the player
        super(Zone.BATTLEFIELD, new DoIfCostPaid(new SludgeStriderEffect(), new GenericManaCost(1)), false);
    }

    public SludgeStriderTriggeredAbility(final SludgeStriderTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent != null && filter.match(permanent, getSourceId(), getControllerId(), game)) {
                return true;
            }
        }
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.getFromZone() == Zone.BATTLEFIELD) {
                UUID targetId = event.getTargetId();
                Permanent permanent = game.getPermanent(targetId);
                if (permanent == null) {
                    permanent = (Permanent) game.getLastKnownInformation(targetId, Zone.BATTLEFIELD);
                }
                if (permanent != null && filter.match(permanent, getSourceId(), getControllerId(), game)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public SludgeStriderTriggeredAbility copy() {
        return new SludgeStriderTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever another artifact enters the battlefield under your control or another artifact you control leaves the battlefield, you may pay {1}. If you do, target player loses 1 life and you gain 1 life.";
    }
}

class SludgeStriderEffect extends OneShotEffect<SludgeStriderEffect> {

    SludgeStriderEffect() {
        super(Outcome.Detriment);
        staticText = "target player loses 1 life and you gain 1 life";
    }

    SludgeStriderEffect(final SludgeStriderEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        Player you = game.getPlayer(source.getControllerId());
        if (targetPlayer != null) {
            targetPlayer.loseLife(1, game);
        }
        if (you != null) {
            you.gainLife(1, game);
        }
        return true;
    }

    @Override
    public SludgeStriderEffect copy() {
        return new SludgeStriderEffect(this);
    }
}
