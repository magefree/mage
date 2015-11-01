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
package mage.sets.gatecrash;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCounterCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class ZameckGuildmage extends CardImpl {

    public ZameckGuildmage(UUID ownerId) {
        super(ownerId, 209, "Zameck Guildmage", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{G}{U}");
        this.expansionSetCode = "GTC";
        this.subtype.add("Elf");
        this.subtype.add("Wizard");

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {G}{U}: This turn, each creature you control enters the battlefield with an additional +1/+1 counter on it.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new ZameckGuildmageEntersBattlefieldEffect(), new ManaCostsImpl("{G}{U}")));

        // {G}{U}, Remove a +1/+1 counter from a creature you control: Draw a card.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), new ManaCostsImpl("{G}{U}"));
        ability.addCost(new RemoveCounterCost(new TargetControlledCreaturePermanent(), CounterType.P1P1));
        this.addAbility(ability);
    }

    public ZameckGuildmage(final ZameckGuildmage card) {
        super(card);
    }

    @Override
    public ZameckGuildmage copy() {
        return new ZameckGuildmage(this);
    }
}

class ZameckGuildmageEntersBattlefieldEffect extends ReplacementEffectImpl {

    public ZameckGuildmageEntersBattlefieldEffect() {
        super(Duration.EndOfTurn, Outcome.BoostCreature);
        this.staticText = "This turn, each creature you control enters the battlefield with an additional +1/+1 counter on it";
    }

    public ZameckGuildmageEntersBattlefieldEffect(ZameckGuildmageEntersBattlefieldEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType().equals(GameEvent.EventType.ENTERS_THE_BATTLEFIELD);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = ((EntersTheBattlefieldEvent) event).getTarget();
        return permanent != null && permanent.getControllerId().equals(source.getControllerId()) && permanent.getCardType().contains(CardType.CREATURE);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent target = ((EntersTheBattlefieldEvent) event).getTarget();
        if (target != null) {
            target.addCounters(CounterType.P1P1.createInstance(), game);
        }
        return false;
    }

    @Override
    public ZameckGuildmageEntersBattlefieldEffect copy() {
        return new ZameckGuildmageEntersBattlefieldEffect(this);
    }
}
