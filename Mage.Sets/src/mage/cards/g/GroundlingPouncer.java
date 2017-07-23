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

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.OpponentControlsPermanentCondition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.EffectType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;

/**
 *
 * @author jeffwadsworth
 *
 */
public class GroundlingPouncer extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public GroundlingPouncer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G/U}");
        this.subtype.add("Faerie");

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {GU}: Groundling Pouncer gets +1/+3 and gains flying until end of turn. Activate this ability only once each turn and only if an opponent controls a creature with flying.
        Ability ability = new GroundlingPouncerAbility(
                Zone.BATTLEFIELD,
                new BoostSourceEffect(1, 3, Duration.EndOfTurn),
                new ManaCostsImpl("{G/U}"),
                new OpponentControlsPermanentCondition(filter),
                "{G/U}: {this} gets +1/+3 and gains flying until end of turn. Activate this ability only once each turn and only if an opponent controls a creature with flying.");
        ability.addEffect(new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.EndOfTurn, false, true));
        this.addAbility(ability);

    }

    public GroundlingPouncer(final GroundlingPouncer card) {
        super(card);
    }

    @Override
    public GroundlingPouncer copy() {
        return new GroundlingPouncer(this);
    }
}

class GroundlingPouncerAbility extends LimitedTimesPerTurnActivatedAbility {

    private static final Effects emptyEffects = new Effects();

    private final Condition condition;
    private final String ruleText;

    public GroundlingPouncerAbility(Zone zone, Effect effect, Cost cost, Condition condition, String rule) {
        super(zone, effect, cost);
        this.condition = condition;
        this.ruleText = rule;
    }

    public GroundlingPouncerAbility(GroundlingPouncerAbility ability) {
        super(ability);
        this.condition = ability.condition;
        this.ruleText = ability.ruleText;
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
    public GroundlingPouncerAbility copy() {
        return new GroundlingPouncerAbility(this);
    }

    @Override
    public String getRule() {
        return ruleText;
    }
}
