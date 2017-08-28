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
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.filter.FilterPlayer;
import mage.filter.common.FilterPermanentOrPlayer;
import mage.filter.common.FilterPlaneswalkerPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.other.PlayerIdPredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAttackingCreature;
import mage.target.common.TargetPermanentOrPlayer;

/**
 *
 * @author TheElk801
 */
public class PortalMage extends CardImpl {

    public PortalMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add("Human");
        this.subtype.add("Wizard");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Portal Mage enters the battlefield during the declare attackers step, you may reselect which player or planeswalker target attacking creature is attacking.
        Ability ability = new PortalMageTriggeredAbility(new PortalMageEffect());
        ability.addTarget(new TargetAttackingCreature());
        this.addAbility(ability);
    }

    public PortalMage(final PortalMage card) {
        super(card);
    }

    @Override
    public PortalMage copy() {
        return new PortalMage(this);
    }
}

class PortalMageTriggeredAbility extends TriggeredAbilityImpl {

    PortalMageTriggeredAbility(Effect effect) {
        super(Zone.ALL, effect, true);
    }

    PortalMageTriggeredAbility(final PortalMageTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PortalMageTriggeredAbility copy() {
        return new PortalMageTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getStep().getType().equals(PhaseStep.DECLARE_ATTACKERS)) {
            return event.getTargetId().equals(getSourceId());
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When {this} enters the battlefield during the declare attackers step, " + super.getRule();
    }
}

class PortalMageEffect extends OneShotEffect {

    PortalMageEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may reselect which player or planeswalker target attacking creature is attacking";
    }

    PortalMageEffect(final PortalMageEffect effect) {
        super(effect);
    }

    @Override
    public PortalMageEffect copy() {
        return new PortalMageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(source.getFirstTarget());
        Player you = game.getPlayer(source.getControllerId());
        if (creature != null && you != null && game.getCombat().getAttackers().contains(creature.getId())) {
            Player controller = game.getPlayer(creature.getControllerId());
            if (controller != null) {
                FilterPermanentOrPlayer filter = new FilterPermanentOrPlayer("player or planeswalker it can attack");
                FilterPlaneswalkerPermanent pwalkerFilter = new FilterPlaneswalkerPermanent();
                pwalkerFilter.add(Predicates.not(new ControllerIdPredicate(controller.getId())));
                filter.setPermanentFilter(pwalkerFilter);
                FilterPlayer playerFilter = new FilterPlayer();
                playerFilter.add(Predicates.not(new PlayerIdPredicate(controller.getId())));
                filter.setPlayerFilter(playerFilter);
                TargetPermanentOrPlayer target = new TargetPermanentOrPlayer(1, 1, true);
                target.setFilter(filter);
                if (you.choose(Outcome.Benefit, target, source.getSourceId(), game)) {
                    if (game.getCombat().canDefenderBeAttacked(creature.getId(), target.getFirstTarget(), game)) {
                        game.getCombat().removeFromCombat(creature.getId(), game, false);
                        game.getCombat().addAttackerToCombat(creature.getId(), target.getFirstTarget(), game);
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
