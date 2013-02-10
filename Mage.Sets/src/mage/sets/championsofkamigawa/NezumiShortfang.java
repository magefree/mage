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
package mage.sets.championsofkamigawa;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.TargetController;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.FlippedCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinousEffect;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CopyTokenEffect;
import mage.abilities.effects.common.DiscardTargetEffect;
import mage.abilities.effects.common.FlipSourceEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public class NezumiShortfang extends CardImpl<NezumiShortfang> {

    public NezumiShortfang(UUID ownerId) {
        super(ownerId, 131, "Nezumi Shortfang", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.expansionSetCode = "CHK";
        this.subtype.add("Rat");
        this.subtype.add("Rogue");

        this.color.setBlack(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.flipCard = true;
        this.flipCardName = "Stabwhisker the Odious";

        // {1}{B}, {tap}: Target opponent discards a card. Then if that player has no cards in hand, flip Nezumi Shortfang.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DiscardTargetEffect(1), new ManaCostsImpl("{1}{B}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetOpponent());
        ability.addEffect(new ConditionalOneShotEffect(
                    new FlipSourceEffect(),
                    new CardsInTargetOpponentHandCondition(CardsInTargetOpponentHandCondition.CountType.FEWER_THAN, 1),
                    "Then if that player has no cards in hand, flip {this}"));
        this.addAbility(ability);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinousEffect(new CopyTokenEffect(new StabwhiskerTheOdious()), FlippedCondition.getInstance(), "")));
    }

    public NezumiShortfang(final NezumiShortfang card) {
        super(card);
    }

    @Override
    public NezumiShortfang copy() {
        return new NezumiShortfang(this);
    }
}

class StabwhiskerTheOdious extends Token {

    StabwhiskerTheOdious() {
        super("Stabwhisker the Odious", "");
        supertype.add("Legendary");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add("Rat");
        subtype.add("Shaman");
        power = new MageInt(3);
        toughness = new MageInt(3);

        // At the beginning of each opponent's upkeep, that player loses 1 life for each card fewer than three in his or her hand.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                Zone.BATTLEFIELD, new StabwhiskerLoseLifeEffect(), TargetController.OPPONENT, false, true));
    }
}

class StabwhiskerLoseLifeEffect extends OneShotEffect<StabwhiskerLoseLifeEffect> {

    public StabwhiskerLoseLifeEffect() {
        super(Outcome.LoseLife);
        this.staticText = "that player loses 1 life for each card fewer than three in his or her hand";
    }

    public StabwhiskerLoseLifeEffect(final StabwhiskerLoseLifeEffect effect) {
        super(effect);
    }

    @Override
    public StabwhiskerLoseLifeEffect copy() {
        return new StabwhiskerLoseLifeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (opponent != null) {
            int lifeLose = 3 - opponent.getHand().size();
            if (lifeLose > 0 ) {
                opponent.loseLife(lifeLose, game);
            }
            return true;
        }
        return false;
    }
}

class CardsInTargetOpponentHandCondition implements Condition {

    public static enum CountType { MORE_THAN, FEWER_THAN, EQUAL_TO };
    private Condition condition;
    private CountType type;
    private int count;

        public CardsInTargetOpponentHandCondition() {
            this(CountType.EQUAL_TO, 0);
        }

    public CardsInTargetOpponentHandCondition (CountType type, int count ) {
        this.type = type;
        this.count = count;
    }

    public CardsInTargetOpponentHandCondition (CountType type, int count, Condition conditionToDecorate ) {
        this(type, count);
        this.condition = conditionToDecorate;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean conditionApplies = false;
        Player opponent = game.getPlayer(source.getFirstTarget());
        if (opponent == null) {
            return false;
        }
        switch ( this.type ) {
            case FEWER_THAN:
                conditionApplies = opponent.getHand().size() < this.count;
                break;
            case MORE_THAN:
                conditionApplies = opponent.getHand().size()  > this.count;
                break;
            case EQUAL_TO:
                conditionApplies = opponent.getHand().size()  == this.count;
                break;
        }

        //If a decorated condition exists, check it as well and apply them together.
        if ( this.condition != null ) {
            conditionApplies = conditionApplies && this.condition.apply(game, source);
        }

        return conditionApplies;
    }
}
