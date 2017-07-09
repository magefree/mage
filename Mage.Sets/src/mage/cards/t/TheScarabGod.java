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

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PutTokenOntoBattlefieldCopyTargetEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author spjspj
 */
public class TheScarabGod extends CardImpl {

    public TheScarabGod(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{B}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add("God");
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // At the beginning of your upkeep, each opponent loses X life and you scry X, where X is the number of Zombies you control.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new TheScarabGodEffect(), TargetController.YOU, false));

        // {2}{U}{B}: Exile target creature card from a graveyard. Create a token that's a copy of it, except it's a 4/4 black Zombie.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new TheScarabGodEffect2(), new ManaCostsImpl("{2}{U}{B}"));
        ability.addTarget(new TargetCardInGraveyard(1, 1, new FilterCreatureCard("creature card from a graveyard")));
        this.addAbility(ability);

        // When The Scarab God dies, return it to its owner's hand at the beginning of the next end step.
        this.addAbility(new DiesTriggeredAbility(new TheScarabGodEffect3()));
    }

    public TheScarabGod(final TheScarabGod card) {
        super(card);
    }

    @Override
    public TheScarabGod copy() {
        return new TheScarabGod(this);
    }
}

class TheScarabGodEffect extends OneShotEffect {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("untapped Zombies you control");

    static {
        filter.add(new SubtypePredicate(SubType.ZOMBIE));
    }

    public TheScarabGodEffect() {
        super(Outcome.Benefit);
        staticText = "each opponent loses X life and you scry X, where X is the number of Zombies you control";
    }

    public TheScarabGodEffect(final TheScarabGodEffect effect) {
        super(effect);
    }

    @Override
    public TheScarabGodEffect copy() {
        return new TheScarabGodEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int numZombies = game.getBattlefield().countAll(filter, source.getControllerId(), game);

            if (numZombies > 0) {
                for (UUID playerId : game.getOpponents(source.getControllerId())) {
                    Player opponent = game.getPlayer(playerId);
                    if (opponent != null) {
                        opponent.loseLife(numZombies, game, false);
                    }
                }
                controller.scry(numZombies, source, game);
            }

            return true;
        }
        return false;
    }
}

class TheScarabGodEffect2 extends OneShotEffect {

    public TheScarabGodEffect2() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Exile target creature card from a graveyard. Create a token that's a copy of it, except it's a 4/4 black Zombie.";
    }

    public TheScarabGodEffect2(final TheScarabGodEffect2 effect) {
        super(effect);
    }

    @Override
    public TheScarabGodEffect2 copy() {
        return new TheScarabGodEffect2(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && card != null) {
            controller.moveCards(card, Zone.EXILED, source, game); // Also if the move to exile is replaced, the copy takes place
            PutTokenOntoBattlefieldCopyTargetEffect effect = new PutTokenOntoBattlefieldCopyTargetEffect(source.getControllerId(), null, false, 1, false, false, null, 4, 4, false);
            effect.setTargetPointer(new FixedTarget(card.getId(), card.getZoneChangeCounter(game)));
            effect.setOnlySubType("Zombie");
            effect.setOnlyColor(ObjectColor.BLACK);
            effect.apply(game, source);
            return true;
        }

        return false;
    }
}

class TheScarabGodEffect3 extends OneShotEffect {

    private static final String effectText = "return it to its owner's hand at the beginning of the next upkeep.";

    TheScarabGodEffect3() {
        super(Outcome.Benefit);
        staticText = effectText;
    }

    TheScarabGodEffect3(TheScarabGodEffect3 effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // Create delayed triggered ability
        Effect effect = new ReturnToHandSourceEffect(false, true);
        effect.setText(staticText);
        DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect);
        game.addDelayedTriggeredAbility(delayedAbility, source);
        return true;
    }

    @Override
    public TheScarabGodEffect3 copy() {
        return new TheScarabGodEffect3(this);
    }
}
