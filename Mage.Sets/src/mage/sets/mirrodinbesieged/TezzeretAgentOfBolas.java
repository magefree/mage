/*
 *  Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
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
package mage.sets.mirrodinbesieged;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.continuous.AddCardTypeTargetEffect;
import mage.abilities.effects.common.continuous.SetPowerToughnessTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetArtifactPermanent;

import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TezzeretAgentOfBolas extends CardImpl {

    private static final FilterCard filter = new FilterCard("an artifact card");
    static {
        filter.add(new CardTypePredicate(CardType.ARTIFACT));
    }

    public TezzeretAgentOfBolas(UUID ownerId) {
        super(ownerId, 97, "Tezzeret, Agent of Bolas", Rarity.MYTHIC, new CardType[]{CardType.PLANESWALKER}, "{2}{U}{B}");
        this.expansionSetCode = "MBS";
        this.subtype.add("Tezzeret");
        this.color.setBlue(true);
        this.color.setBlack(true);
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.LOYALTY.createInstance(3)), false));

        // +1: Look at the top five cards of your library. You may reveal an artifact card from among them and put it into your hand. Put the rest on the bottom of your library in any order.
        this.addAbility(new LoyaltyAbility(new LookLibraryAndPickControllerEffect(5, 1, filter, true), 1));

        // -1: Target artifact becomes an artifact creature with base power and toughness 5/5.
        Effect effect = new AddCardTypeTargetEffect(CardType.CREATURE, Duration.EndOfGame);
        effect.setText("");
        LoyaltyAbility ability1 = new LoyaltyAbility(effect, -1);
        effect = new SetPowerToughnessTargetEffect(5,5, Duration.EndOfGame);
        effect.setText("Target artifact becomes an artifact creature with base power and toughness 5/5");
        ability1.addEffect(effect);
        ability1.addTarget(new TargetArtifactPermanent());
        this.addAbility(ability1);

        // -4: Target player loses X life and you gain X life, where X is twice the number of artifacts you control.
        LoyaltyAbility ability2 = new LoyaltyAbility(new TezzeretAgentOfBolasEffect2(), -4);
        ability2.addTarget(new TargetPlayer());
        this.addAbility(ability2);

    }

    public TezzeretAgentOfBolas(final TezzeretAgentOfBolas card) {
        super(card);
    }

    @Override
    public TezzeretAgentOfBolas copy() {
        return new TezzeretAgentOfBolas(this);
    }

}

class TezzeretAgentOfBolasEffect2 extends OneShotEffect {

    final static FilterControlledPermanent filter = new FilterControlledPermanent("artifacts");

    static {
        filter.add(new CardTypePredicate(CardType.ARTIFACT));
    }

    public TezzeretAgentOfBolasEffect2() {
        super(Outcome.DrawCard);
        staticText = "Target player loses X life and you gain X life, where X is twice the number of artifacts you control";
    }

    public TezzeretAgentOfBolasEffect2(final TezzeretAgentOfBolasEffect2 effect) {
        super(effect);
    }

    @Override
    public TezzeretAgentOfBolasEffect2 copy() {
        return new TezzeretAgentOfBolasEffect2(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        DynamicValue value = new PermanentsOnBattlefieldCount(filter);
        int count = value.calculate(game, source, this) * 2;

        Player player = game.getPlayer(source.getFirstTarget());
        if (player != null) {
            player.loseLife(count, game);
        }
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.gainLife(count, game);
        }
        return true;
    }

}
