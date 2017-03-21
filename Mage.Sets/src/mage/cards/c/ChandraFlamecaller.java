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
package mage.cards.c;

import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PayVariableLoyaltyCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.token.ElementalToken;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public class ChandraFlamecaller extends CardImpl {

    public ChandraFlamecaller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.PLANESWALKER},"{4}{R}{R}");
        this.subtype.add("Chandra");

        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(4));

        // +1: Create two 3/1 red Elemental creature tokens with haste. Exile them at the beginning of the next end step.
        this.addAbility(new LoyaltyAbility(new ChandraElementalEffect(), 1));

        // 0: Discard all the cards in your hand, then draw that many cards plus one.
        this.addAbility(new LoyaltyAbility(new ChandraDrawEffect(), 0));

        // -X: Chandra, Flamecaller deals X damage to each creature.
        this.addAbility(new LoyaltyAbility(new DamageAllEffect(ChandraXValue.getDefault(), new FilterCreaturePermanent("creature"))));
    }

    public ChandraFlamecaller(final ChandraFlamecaller card) {
        super(card);
    }

    @Override
    public ChandraFlamecaller copy() {
        return new ChandraFlamecaller(this);
    }
}

class ChandraElementalEffect extends OneShotEffect {

    public ChandraElementalEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Create two 3/1 red Elemental creature tokens with haste. Exile them at the beginning of the next end step";
    }

    public ChandraElementalEffect(final ChandraElementalEffect effect) {
        super(effect);
    }

    @Override
    public ChandraElementalEffect copy() {
        return new ChandraElementalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            CreateTokenEffect effect = new CreateTokenEffect(new ElementalToken("OGW", 1, true), 2);
            effect.apply(game, source);
            effect.exileTokensCreatedAtNextEndStep(game, source);
            return true;
        }

        return false;
    }
}

class ChandraDrawEffect extends OneShotEffect {

    ChandraDrawEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Discard all the cards in your hand, then draw that many cards plus one";
    }

    ChandraDrawEffect(final ChandraDrawEffect effect) {
        super(effect);
    }

    @Override
    public ChandraDrawEffect copy() {
        return new ChandraDrawEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Set<Card> cardsInHand = player.getHand().getCards(game);
            int amount = cardsInHand.size();
            for (Card card : cardsInHand) {
                player.discard(card, source, game);
            }
            player.drawCards(amount + 1, game);
            return true;
        }
        return false;
    }
}

class ChandraXValue implements DynamicValue {

    private static final ChandraXValue defaultValue = new ChandraXValue();

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        for (Cost cost : sourceAbility.getCosts()) {
            if (cost instanceof PayVariableLoyaltyCost) {
                return ((PayVariableLoyaltyCost) cost).getAmount();
            }
        }
        return 0;
    }

    @Override
    public DynamicValue copy() {
        return defaultValue;
    }

    @Override
    public String getMessage() {
        return "";
    }

    @Override
    public String toString() {
        return "X";
    }

    public static ChandraXValue getDefault() {
        return defaultValue;
    }
}
