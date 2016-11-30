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
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RedirectionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author MTGfan
 */
public class PersonalIncarnation extends CardImpl {

    public PersonalIncarnation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}{W}");
        
        this.subtype.add("Avatar");
        this.subtype.add("Incarnation");
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // {0}: The next 1 damage that would be dealt to Personal Incarnation this turn is dealt to its owner instead. Only Personal Incarnation's owner may activate this ability.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PersonalIncarnationRedirectEffect(), new GenericManaCost(0));
        ability.setMayActivate(TargetController.OWNER);
        this.addAbility(ability);
        // When Personal Incarnation dies, its owner loses half his or her life, rounded up.
        this.addAbility(new DiesTriggeredAbility(new PersonalIncarnationLoseHalfLifeEffect()));
    }

    public PersonalIncarnation(final PersonalIncarnation card) {
        super(card);
    }

    @Override
    public PersonalIncarnation copy() {
        return new PersonalIncarnation(this);
    }
}

class PersonalIncarnationRedirectEffect extends RedirectionEffect {

    public PersonalIncarnationRedirectEffect() {
        super(Duration.EndOfTurn, 1, true);
        staticText = "The next 1 damage that would be dealt to {this} this turn is dealt to its owner instead.";
    }

    public PersonalIncarnationRedirectEffect(final PersonalIncarnationRedirectEffect effect) {
        super(effect);
    }

    @Override
    public PersonalIncarnationRedirectEffect copy() {
        return new PersonalIncarnationRedirectEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getTargetId().equals(source.getSourceId())) {
            TargetPlayer target = new TargetPlayer();
            target.add(game.getOwnerId(source.getSourceId()), game);
            redirectTarget = target;
            return true;
        }
        return false;
    }
}

class PersonalIncarnationLoseHalfLifeEffect extends OneShotEffect {

    public PersonalIncarnationLoseHalfLifeEffect() {
        super(Outcome.LoseLife);
        staticText = "its owner lose half his or her life, rounded up";
    }

    public PersonalIncarnationLoseHalfLifeEffect(final PersonalIncarnationLoseHalfLifeEffect effect) {
        super(effect);
    }

    @Override
    public PersonalIncarnationLoseHalfLifeEffect copy() {
        return new PersonalIncarnationLoseHalfLifeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(game.getOwnerId(source.getSourceId()));
        if (player != null) {
            int amount = (player.getLife() + 1) / 2;
            if (amount > 0) {
                player.loseLife(amount, game, false);
                return true;
            }
        }
        return false;
    }
}
