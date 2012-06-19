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
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.Constants.TurnPhase;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.effects.common.continious.BecomesCreatureSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.PreventAllDamageSourceEffect;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class GideonJura extends CardImpl<GideonJura> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("tapped creature");

    static {
        filter.setUseTapped(true);
        filter.setTapped(true);
    }

    public GideonJura(UUID ownerId) {
        super(ownerId, 21, "Gideon Jura", Rarity.MYTHIC, new CardType[]{CardType.PLANESWALKER}, "{3}{W}{W}");
        this.expansionSetCode = "ROE";
        this.subtype.add("Gideon");
        this.color.setWhite(true);
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.LOYALTY.createInstance(6)), ""));

        LoyaltyAbility ability1 = new LoyaltyAbility(new GideonJuraEffect(), 2);
        ability1.addTarget(new TargetOpponent());
        this.addAbility(ability1);

        LoyaltyAbility ability2 = new LoyaltyAbility(new DestroyTargetEffect(), -2);
        ability2.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability2);

        LoyaltyAbility ability3 = new LoyaltyAbility(new BecomesCreatureSourceEffect(new GideonJuraToken(), "planeswalker", Duration.EndOfTurn), 0);
        ability3.addEffect(new PreventAllDamageSourceEffect(Duration.EndOfTurn));
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

class GideonJuraEffect extends RequirementEffect<GideonJuraEffect> {

    public GideonJuraEffect() {
        super(Duration.Custom);
        staticText = "During target opponent's next turn, creatures that player controls attack {this} if able";
    }

    public GideonJuraEffect(final GideonJuraEffect effect) {
        super(effect);
    }

    @Override
    public GideonJuraEffect copy() {
        return new GideonJuraEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getControllerId().equals(source.getFirstTarget());
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        return (game.getPhase().getType() == TurnPhase.END && game.getActivePlayerId().equals(source.getFirstTarget()));
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