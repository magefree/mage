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
package mage.cards.t;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PutPermanentOnBattlefieldEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.watchers.Watcher;

/**
 *
 * @author TheElk801
 */
public class TheUrDragon extends CardImpl {

    private static final FilterCard filter = new FilterCard("Dragon spells");

    static {
        filter.add(new SubtypePredicate(SubType.DRAGON));
    }

    public TheUrDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{U}{B}{R}{G}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add("Dragon");
        this.subtype.add("Avatar");
        this.power = new MageInt(10);
        this.toughness = new MageInt(10);

        // <i>Eminence</i> - As long as The Ur-Dragon is in the command zone or on the battlefield, other Dragon spells you cast cost {1} less to cast.
        Effect effect = new SpellsCostReductionControllerEffect(filter, 1);
        effect.setText("As long as {this} is in the command zone or on the battlefield, other Dragon spells you cast cost {1} less to cast");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        ability.setAbilityWord(AbilityWord.EMINENCE);
        this.addAbility(ability);
        effect = new SpellsCostReductionControllerEffect(filter, 1);
        effect.setText("");
        ability = new SimpleStaticAbility(Zone.COMMAND, effect);
        this.addAbility(ability);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever one or more Dragons you control attack, draw that many cards, then you may put a permanent card from your hand onto the battlefield
        this.addAbility(new TheUrDragonTriggeredAbility(), new DragonsAttackedWatcher());
    }

    public TheUrDragon(final TheUrDragon card) {
        super(card);
    }

    @Override
    public TheUrDragon copy() {
        return new TheUrDragon(this);
    }
}

class DragonsAttackedWatcher extends Watcher {

    public final Set<MageObjectReference> attackedThisTurnCreatures = new HashSet<>();

    public DragonsAttackedWatcher() {
        super(DragonsAttackedWatcher.class.getSimpleName(), WatcherScope.GAME);
    }

    public DragonsAttackedWatcher(final DragonsAttackedWatcher watcher) {
        super(watcher);
        this.attackedThisTurnCreatures.addAll(watcher.attackedThisTurnCreatures);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.BEGIN_COMBAT_STEP_PRE) {
            this.attackedThisTurnCreatures.clear();
        }
        if (event.getType() == GameEvent.EventType.ATTACKER_DECLARED) {
            if (game.getPermanent(event.getSourceId()).getSubtype(game).contains(SubType.DRAGON)) {
                this.attackedThisTurnCreatures.add(new MageObjectReference(event.getSourceId(), game));
            }
        }
    }

    public Set<MageObjectReference> getAttackedThisTurnCreatures() {
        return this.attackedThisTurnCreatures;
    }

    @Override
    public DragonsAttackedWatcher copy() {
        return new DragonsAttackedWatcher(this);
    }

}

class TheUrDragonTriggeredAbility extends TriggeredAbilityImpl {

    public TheUrDragonTriggeredAbility() {
        super(Zone.BATTLEFIELD, new TheUrDragonEffect(), false);
    }

    public TheUrDragonTriggeredAbility(final TheUrDragonTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TheUrDragonTriggeredAbility copy() {
        return new TheUrDragonTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        for (UUID attacker : game.getCombat().getAttackers()) {
            Permanent creature = game.getPermanent(attacker);
            if (creature != null && creature.getControllerId() == event.getPlayerId() && creature.getSubtype(game).contains(SubType.DRAGON)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever one or more Dragons you control attack, " + super.getRule();
    }
}

class TheUrDragonEffect extends OneShotEffect {

    public TheUrDragonEffect() {
        super(Outcome.Benefit);
        this.staticText = "draw that many cards, then you may put a permanent card from your hand onto the battlefield.";
    }

    public TheUrDragonEffect(final TheUrDragonEffect effect) {
        super(effect);
    }

    @Override
    public TheUrDragonEffect copy() {
        return new TheUrDragonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            DragonsAttackedWatcher watcher = (DragonsAttackedWatcher) game.getState().getWatchers().get(DragonsAttackedWatcher.class.getSimpleName());
            if (watcher != null) {
                int attackingDragons = 0;
                for (MageObjectReference attacker : watcher.getAttackedThisTurnCreatures()) {
                    if (attacker.getPermanentOrLKIBattlefield(game).getControllerId().equals(controller.getId())) {
                        attackingDragons++;
                    }
                }
                if (attackingDragons > 0) {
                    controller.drawCards(attackingDragons, game);
                }
                Effect effect = new PutPermanentOnBattlefieldEffect();
                effect.apply(game, source);
                return true;
            }
        }
        return false;
    }
}
