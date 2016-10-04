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
package mage.sets.timespiral;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.turn.SkipNextTurnSourceEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.EffectType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;

/**
 *
 * @author emerald000
 */
public class ChronatogTotem extends CardImpl {

    public ChronatogTotem(UUID ownerId) {
        super(ownerId, 252, "Chronatog Totem", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.expansionSetCode = "TSP";

        // {tap}: Add {U} to your mana pool.
        this.addAbility(new BlueManaAbility());
        
        // {1}{U}: Chronatog Totem becomes a 1/2 blue Atog artifact creature until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesCreatureSourceEffect(new ChronatogTotemToken(), "", Duration.EndOfTurn), new ManaCostsImpl<>("{1}{U}")));
        
        // {0}: Chronatog Totem gets +3/+3 until end of turn. You skip your next turn. Activate this ability only once each turn and only if Chronatog Totem is a creature.
        Ability ability = new ChronatogTotemAbility(
                Zone.BATTLEFIELD,
                new BoostSourceEffect(3, 3, Duration.EndOfTurn),
                new ManaCostsImpl<>("{0}"),
                new ChronatogTotemCondition());
        ability.addEffect(new SkipNextTurnSourceEffect());
        this.addAbility(ability);
    }

    public ChronatogTotem(final ChronatogTotem card) {
        super(card);
    }

    @Override
    public ChronatogTotem copy() {
        return new ChronatogTotem(this);
    }
}

class ChronatogTotemAbility extends LimitedTimesPerTurnActivatedAbility {

    private static final Effects emptyEffects = new Effects();

    private final Condition condition;

    public ChronatogTotemAbility(Zone zone, Effect effect, Cost cost, Condition condition) {
        super(zone, effect, cost);
        this.condition = condition;
    }

    public ChronatogTotemAbility(ChronatogTotemAbility ability) {
        super(ability);
        this.condition = ability.condition;
    }

    @Override
    public Effects getEffects(Game game, EffectType effectType) {
        if (!condition.apply(game, this)) {
            return emptyEffects;
        }
        return super.getEffects(game, effectType);
    }

    @Override
    public boolean canActivate(UUID playerId, Game game) {
        if (!condition.apply(game, this)) {
            return false;
        }
        return super.canActivate(playerId, game);
    }

    @Override
    public ChronatogTotemAbility copy() {
        return new ChronatogTotemAbility(this);
    }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder(super.getRule());
        sb.deleteCharAt(sb.length() - 1); // remove last '.'
        sb.append(" and only if ").append(condition.toString()).append(".");
        return sb.toString();
    }
}

class ChronatogTotemToken extends Token {

    ChronatogTotemToken() {
        super("Atog", "1/2 blue Atog artifact creature");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add("Atog");
        power = new MageInt(1);
        toughness = new MageInt(2);
        color.setBlue(true);
    }
}

class ChronatogTotemCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            return permanent.getCardType().contains(CardType.CREATURE);
        }
        return false;
    }

    @Override
    public String toString() {
        return "{this} is a creature";
    }
}
