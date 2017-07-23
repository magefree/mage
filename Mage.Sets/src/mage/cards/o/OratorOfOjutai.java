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
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.watchers.common.DragonOnTheBattlefieldWhileSpellWasCastWatcher;

/**
 *
 * @author LevelX2
 */
public class OratorOfOjutai extends CardImpl {

    private static final FilterCard filter = new FilterCard("a Dragon card from your hand (you don't have to)");

    static {
        filter.add(new SubtypePredicate(SubType.DRAGON));
    }

    public OratorOfOjutai(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.subtype.add("Bird");
        this.subtype.add("Monk");
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Defender, flying
        this.addAbility(DefenderAbility.getInstance());
        this.addAbility(FlyingAbility.getInstance());

        // As an additional cost to cast Orator of Ojutai, you may reveal a Dragon card from your hand.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new InfoEffect("As an additional cost to cast {this}, you may reveal a Dragon card from your hand")));

        // When Orator of Ojutai enters the battlefield, if you revealed a Dragon card or controlled a Dragon as you cast Orator of Ojutai, draw a card.
        this.addAbility(new OratorOfOjutaiTriggeredAbility(new OratorOfOjutaiEffect()), new DragonOnTheBattlefieldWhileSpellWasCastWatcher());
    }

    @Override
    public void adjustCosts(Ability ability, Game game) {
        if (ability.getAbilityType() == AbilityType.SPELL) {
            Player controller = game.getPlayer(ability.getControllerId());
            if (controller != null) {
                if (controller.getHand().count(filter, game) > 0) {
                    ability.addCost(new RevealTargetFromHandCost(new TargetCardInHand(0, 1, filter)));
                }
            }
        }
    }

    public OratorOfOjutai(final OratorOfOjutai card) {
        super(card);
    }

    @Override
    public OratorOfOjutai copy() {
        return new OratorOfOjutai(this);
    }
}

class OratorOfOjutaiTriggeredAbility extends TriggeredAbilityImpl {

    public OratorOfOjutaiTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, false);
    }

    public OratorOfOjutaiTriggeredAbility(final OratorOfOjutaiTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        //Intervening if must be checked
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(getSourceId());
        DragonOnTheBattlefieldWhileSpellWasCastWatcher watcher = (DragonOnTheBattlefieldWhileSpellWasCastWatcher) game.getState().getWatchers().get(DragonOnTheBattlefieldWhileSpellWasCastWatcher.class.getSimpleName());
        return event.getTargetId().equals(getSourceId())
                && watcher != null
                && watcher.castWithConditionTrue(sourcePermanent.getSpellAbility().getId());
    }

    @Override
    public String getRule() {
        return "When {this} enters the battlefield, " + super.getRule();
    }

    @Override
    public OratorOfOjutaiTriggeredAbility copy() {
        return new OratorOfOjutaiTriggeredAbility(this);
    }
}

class OratorOfOjutaiEffect extends OneShotEffect {

    public OratorOfOjutaiEffect() {
        super(Outcome.Benefit);
        this.staticText = "If you revealed a Dragon card or controlled a Dragon as you cast {this}, draw a card";
    }

    public OratorOfOjutaiEffect(final OratorOfOjutaiEffect effect) {
        super(effect);
    }

    @Override
    public OratorOfOjutaiEffect copy() {
        return new OratorOfOjutaiEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        //Intervening if is checked again on resolution
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
            if (sourcePermanent != null) {
                DragonOnTheBattlefieldWhileSpellWasCastWatcher watcher = (DragonOnTheBattlefieldWhileSpellWasCastWatcher) game.getState().getWatchers().get(DragonOnTheBattlefieldWhileSpellWasCastWatcher.class.getSimpleName());
                if (watcher != null && watcher.castWithConditionTrue(sourcePermanent.getSpellAbility().getId())) {
                    controller.drawCards(1, game);
                    return true;
                }
            }
        }
        return false;
    }
}
