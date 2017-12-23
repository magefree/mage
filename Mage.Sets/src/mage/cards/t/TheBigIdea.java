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
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.BrainiacToken;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author spjspj
 */
public class TheBigIdea extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent(SubType.BRAINIAC, "Brainiac creatures");

    static {
        filter.add(Predicates.not(new TappedPredicate()));
    }

    public TheBigIdea(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.BRAINIAC);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // 2{BR}{BR}, T: Roll a six-sided dice. Create a number of 1/1 red Brainiac creature tokens equal to the result. 
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new TheBigIdeaEffect(), new ManaCostsImpl("{2}{B/R}{B/R}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // Tap three untapped Brainiacs you control: The next time you would roll a six-sided die, instead roll two six-sided dice and use the total of those results.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new TheBigIdeaReplacementEffect(), new TapTargetCost(new TargetControlledCreaturePermanent(3, 3, filter, true))));
    }

    public TheBigIdea(final TheBigIdea card) {
        super(card);
    }

    @Override
    public TheBigIdea copy() {
        return new TheBigIdea(this);
    }
}

class TheBigIdeaReplacementEffect extends ReplacementEffectImpl {

    TheBigIdeaReplacementEffect() {
        super(Duration.EndOfTurn, Outcome.Damage);
        staticText = "The next time you would roll a six-sided die, instead roll two six-sided dice and use the total of those results";
    }

    TheBigIdeaReplacementEffect(final TheBigIdeaReplacementEffect effect) {
        super(effect);
    }

    @Override
    public TheBigIdeaReplacementEffect copy() {
        return new TheBigIdeaReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (event.getData() != null) {
            String data = event.getData();
            int numSides = Integer.parseInt(data);
            if (numSides != 6) {
                return false;
            }
        }

        if (controller != null) {
            discard();
            int amount = controller.rollDice(game, 6);
            event.setAmount(event.getAmount() + amount);
            return true;
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ROLL_DICE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!this.used) {
            return source.getControllerId().equals(event.getPlayerId());
        }
        return false;
    }
}

class TheBigIdeaEffect extends OneShotEffect {

    public TheBigIdeaEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Roll a six-sided dice. Create a number of 1/1 red Brainiac creature tokens equal to the result";
    }

    public TheBigIdeaEffect(final TheBigIdeaEffect effect) {
        super(effect);
    }

    @Override
    public TheBigIdeaEffect copy() {
        return new TheBigIdeaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (controller != null && permanent != null) {
            int amount = controller.rollDice(game, 6);
            return new CreateTokenEffect(new BrainiacToken(), amount).apply(game, source);
        }
        return false;
    }
}
