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
package mage.sets.commander2014;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.IntimidateAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LevelX2
 */
public class FleshCarver extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("another creature");

    static {
        filter.add(new AnotherPredicate());
    }

    public FleshCarver(UUID ownerId) {
        super(ownerId, 22, "Flesh Carver", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.expansionSetCode = "C14";
        this.subtype.add("Human");
        this.subtype.add("Wizard");

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Intimidate
        this.addAbility(IntimidateAbility.getInstance());
        // {1}{B}, Sacrifice another creature: Put two +1/+1 counters on Flesh Carver.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)), new ManaCostsImpl("{1}{B}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter)));
        this.addAbility(ability);

        // When Flesh Carver dies, put an X/X black Horror creature token onto the battlefield, where X is Flesh Carver's power.
        this.addAbility(new FleshCarverAbility());
    }

    public FleshCarver(final FleshCarver card) {
        super(card);
    }

    @Override
    public FleshCarver copy() {
        return new FleshCarver(this);
    }
}

class FleshCarverAbility extends DiesTriggeredAbility {
    public FleshCarverAbility() {
        super(new FleshCarverEffect(), false);
    }

    public FleshCarverAbility(final FleshCarverAbility ability) {
        super(ability);
    }

    @Override
    public FleshCarverAbility copy() {
        return new FleshCarverAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (super.checkTrigger(event, game)) {
            Permanent permanent = (Permanent) game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD);
            if (permanent !=null) {
                for (Effect effect :this.getEffects()) {
                    effect.setValue("power", permanent.getPower().getValue());
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When Flesh Carver dies, " + super.getRule();
    }
}

class FleshCarverEffect extends OneShotEffect {

    public FleshCarverEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "put an X/X black Horror creature token onto the battlefield, where X is {this}'s power";
    }

    public FleshCarverEffect(FleshCarverEffect ability) {
        super(ability);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int xValue = (Integer) getValue("power");
            return new CreateTokenEffect(new FleshCarverHorrorToken(xValue)).apply(game, source);
        }
        return false;
    }

    @Override
    public FleshCarverEffect copy() {
        return new FleshCarverEffect(this);
    }

}

class FleshCarverHorrorToken extends Token {

    public FleshCarverHorrorToken(int xValue) {
        super("Horror", "X/X black Horror creature token");
        setOriginalExpansionSetCode("C14");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add("Horror");
        power = new MageInt(xValue);
        toughness = new MageInt(xValue);

    }
}
