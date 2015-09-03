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

package mage.sets.riseoftheeldrazi;

import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.PreventAllDamageToSourceEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.TurnPhase;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class GideonJura extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("tapped creature");

    static {
        filter.add(new TappedPredicate());
    }

    public GideonJura(UUID ownerId) {
        super(ownerId, 21, "Gideon Jura", Rarity.MYTHIC, new CardType[]{CardType.PLANESWALKER}, "{3}{W}{W}");
        this.expansionSetCode = "ROE";
        this.subtype.add("Gideon");


        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.LOYALTY.createInstance(6)), false));
        
        // +2: During target opponent's next turn, creatures that player controls attack Gideon Jura if able.
        LoyaltyAbility ability1 = new LoyaltyAbility(new GideonJuraEffect(), 2);
        ability1.addTarget(new TargetOpponent());
        this.addAbility(ability1);

        // −2: Destroy target tapped creature.
        LoyaltyAbility ability2 = new LoyaltyAbility(new DestroyTargetEffect(), -2);
        ability2.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability2);

        // 0: Until end of turn, Gideon Jura becomes a 6/6 Human Soldier creature that's still a planeswalker. Prevent all damage that would be dealt to him this turn.
        LoyaltyAbility ability3 = new LoyaltyAbility(new BecomesCreatureSourceEffect(new GideonJuraToken(), "planeswalker", Duration.EndOfTurn), 0);
        Effect effect = new PreventAllDamageToSourceEffect(Duration.EndOfTurn);
        effect.setText("Prevent all damage that would be dealt to him this turn");
        ability3.addEffect(effect);
        this.addAbility(ability3);
    }

    public GideonJura(final GideonJura card) {
        super(card);
    }

    @Override
    public GideonJura copy() {
        return new GideonJura(this);
    }

}

class GideonJuraToken extends Token {

    public GideonJuraToken() {
        super("", "6/6 Human Soldier creature");
        cardType.add(CardType.CREATURE);
        subtype.add("Human");
        subtype.add("Soldier");
        power = new MageInt(6);
        toughness = new MageInt(6);
    }

}

class GideonJuraEffect extends RequirementEffect {

    protected MageObjectReference creatingPermanent;

    public GideonJuraEffect() {
        super(Duration.Custom);
        staticText = "During target opponent's next turn, creatures that player controls attack {this} if able";
    }

    public GideonJuraEffect(final GideonJuraEffect effect) {
        super(effect);
        this.creatingPermanent = effect.creatingPermanent;
    }

    @Override
    public GideonJuraEffect copy() {
        return new GideonJuraEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        creatingPermanent = new MageObjectReference(source.getSourceId(), game);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getControllerId().equals(source.getFirstTarget());
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        return (startingTurn != game.getTurnNum() &&
                (game.getPhase().getType() == TurnPhase.END &&
                game.getActivePlayerId().equals(source.getFirstTarget())))
                || // 6/15/2010: If a creature controlled by the affected player can't attack Gideon Jura (because he's no longer on the battlefield, for example), that player may have it attack you, another one of your planeswalkers, or nothing at all.
                creatingPermanent.getPermanent(game) == null;
    }

    @Override
    public UUID mustAttackDefender(Ability source, Game game) {
        return source.getSourceId();
    }

    @Override
    public boolean mustAttack(Game game) {
        return true;
    }

    @Override
    public boolean mustBlock(Game game) {
        return false;
    }
}