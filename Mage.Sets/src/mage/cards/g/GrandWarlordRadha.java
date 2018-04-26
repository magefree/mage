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
package mage.cards.g;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.watchers.Watcher;

/**
 *
 * @author TheElk801
 */
public class GrandWarlordRadha extends CardImpl {

    public GrandWarlordRadha(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever one or more creatures you control attack, add that much mana in any combination of {R} and/or {G}. Until end of turn, you don't lose this mana as steps and phases end.
        this.addAbility(new GrandWarlordRadhaTriggeredAbility(), new CreaturesAttackedWatcher());
    }

    public GrandWarlordRadha(final GrandWarlordRadha card) {
        super(card);
    }

    @Override
    public GrandWarlordRadha copy() {
        return new GrandWarlordRadha(this);
    }
}

class CreaturesAttackedWatcher extends Watcher {

    public final Set<MageObjectReference> attackedThisTurnCreatures = new HashSet<>();

    public CreaturesAttackedWatcher() {
        super(CreaturesAttackedWatcher.class.getSimpleName(), WatcherScope.GAME);
    }

    public CreaturesAttackedWatcher(final CreaturesAttackedWatcher watcher) {
        super(watcher);
        this.attackedThisTurnCreatures.addAll(watcher.attackedThisTurnCreatures);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.BEGIN_COMBAT_STEP_PRE) {
            this.attackedThisTurnCreatures.clear();
        }
        if (event.getType() == GameEvent.EventType.ATTACKER_DECLARED) {
            this.attackedThisTurnCreatures.add(new MageObjectReference(event.getSourceId(), game));
        }
    }

    public Set<MageObjectReference> getAttackedThisTurnCreatures() {
        return this.attackedThisTurnCreatures;
    }

    @Override
    public CreaturesAttackedWatcher copy() {
        return new CreaturesAttackedWatcher(this);
    }

}

class GrandWarlordRadhaTriggeredAbility extends TriggeredAbilityImpl {

    public GrandWarlordRadhaTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GrandWarlordRadhaEffect(), false);
    }

    public GrandWarlordRadhaTriggeredAbility(final GrandWarlordRadhaTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GrandWarlordRadhaTriggeredAbility copy() {
        return new GrandWarlordRadhaTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        for (UUID attacker : game.getCombat().getAttackers()) {
            Permanent creature = game.getPermanent(attacker);
            if (creature != null
                    && creature.getControllerId() != null
                    && creature.getControllerId().equals(this.getControllerId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever one or more creatures you control attack, " + super.getRule();
    }
}

class GrandWarlordRadhaEffect extends OneShotEffect {

    public GrandWarlordRadhaEffect() {
        super(Outcome.Benefit);
        this.staticText = "add that much mana in any combination of {R} and/or {G}. Until end of turn, you don't lose this mana as steps and phases end";
    }

    public GrandWarlordRadhaEffect(final GrandWarlordRadhaEffect effect) {
        super(effect);
    }

    @Override
    public GrandWarlordRadhaEffect copy() {
        return new GrandWarlordRadhaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            CreaturesAttackedWatcher watcher = (CreaturesAttackedWatcher) game.getState().getWatchers().get(CreaturesAttackedWatcher.class.getSimpleName());
            if (watcher != null) {
                int attackingCreatures = 0;
                for (MageObjectReference attacker : watcher.getAttackedThisTurnCreatures()) {
                    if (attacker.getPermanentOrLKIBattlefield(game).getControllerId().equals(controller.getId())) {
                        attackingCreatures++;
                    }
                }
                if (attackingCreatures > 0) {
                    Choice manaChoice = new ChoiceImpl();
                    Set<String> choices = new LinkedHashSet<>();
                    choices.add("Red");
                    choices.add("Green");
                    manaChoice.setChoices(choices);
                    manaChoice.setMessage("Select color of mana to add");

                    for (int i = 0; i < attackingCreatures; i++) {
                        Mana mana = new Mana();
                        if (!controller.choose(Outcome.Benefit, manaChoice, game)) {
                            return false;
                        }
                        if (manaChoice.getChoice() == null) {  // can happen if player leaves game
                            return false;
                        }
                        switch (manaChoice.getChoice()) {
                            case "Green":
                                mana.increaseGreen();
                                break;
                            case "Red":
                                mana.increaseRed();
                                break;
                        }
                        controller.getManaPool().addMana(mana, game, source, true);
                    }
                    return true;
                }
                return true;
            }
        }
        return false;
    }
}
