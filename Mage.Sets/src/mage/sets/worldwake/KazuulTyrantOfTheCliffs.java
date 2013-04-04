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
package mage.sets.worldwake;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public class KazuulTyrantOfTheCliffs extends CardImpl<KazuulTyrantOfTheCliffs> {

    public KazuulTyrantOfTheCliffs(UUID ownerId) {
        super(ownerId, 84, "Kazuul, Tyrant of the Cliffs", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");
        this.expansionSetCode = "WWK";
        this.supertype.add("Legendary");
        this.subtype.add("Ogre");
        this.subtype.add("Warrior");

        this.color.setRed(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Whenever a creature an opponent controls attacks, if you're the defending player, put a 3/3 red Ogre creature token onto the battlefield unless that creature's controller pays {3}.
        this.addAbility(new KazuulTyrantOfTheCliffsTriggeredAbility());
    }

    public KazuulTyrantOfTheCliffs(final KazuulTyrantOfTheCliffs card) {
        super(card);
    }

    @Override
    public KazuulTyrantOfTheCliffs copy() {
        return new KazuulTyrantOfTheCliffs(this);
    }
}

class KazuulTyrantOfTheCliffsTriggeredAbility extends TriggeredAbilityImpl<KazuulTyrantOfTheCliffsTriggeredAbility> {

    public KazuulTyrantOfTheCliffsTriggeredAbility() {
        super(Constants.Zone.BATTLEFIELD, new KazuulTyrantOfTheCliffsEffect(new GenericManaCost(3)));
    }

    public KazuulTyrantOfTheCliffsTriggeredAbility(final KazuulTyrantOfTheCliffsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public KazuulTyrantOfTheCliffsTriggeredAbility copy() {
        return new KazuulTyrantOfTheCliffsTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ATTACKER_DECLARED) {
            Permanent attacker = game.getPermanent(event.getSourceId());
            Player defender = game.getPlayer(event.getTargetId());
            Player you = game.getPlayer(controllerId);
            if (attacker.getControllerId() != you.getId()
                    && defender == you) {
                this.getEffects().get(0).setTargetPointer(new FixedTarget(attacker.getControllerId()));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature an opponent controls attacks, if you're the defending player, put a 3/3 red Ogre creature token onto the battlefield unless that creature's controller pays {3}";
    }
}

class KazuulTyrantOfTheCliffsEffect extends OneShotEffect<KazuulTyrantOfTheCliffsEffect> {

    protected Cost cost;
    private static OgreToken token = new OgreToken();

    public KazuulTyrantOfTheCliffsEffect(Cost cost) {
        super(Constants.Outcome.PutCreatureInPlay);
        this.cost = cost;
    }

    public KazuulTyrantOfTheCliffsEffect(KazuulTyrantOfTheCliffsEffect effect) {
        super(effect);
        this.cost = effect.cost.copy();
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player payee = game.getPlayer(targetPointer.getFirst(game, source));
        if (payee != null) {
            cost.clearPaid();
            if (!cost.pay(source, game, source.getId(), payee.getId(), false)) {
                return token.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId());
            }
        }
        return false;
    }

    @Override
    public KazuulTyrantOfTheCliffsEffect copy() {
        return new KazuulTyrantOfTheCliffsEffect(this);
    }
}

class OgreToken extends Token {

    OgreToken() {
        super("Ogre", "3/3 red Ogre creature");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add("Ogre");
        power = new MageInt(3);
        toughness = new MageInt(3);
    }
}
