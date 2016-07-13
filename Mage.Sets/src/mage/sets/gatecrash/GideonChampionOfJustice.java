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
package mage.sets.gatecrash;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CountersCount;
import mage.abilities.dynamicvalue.common.PermanentsTargetOpponentControlsCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PreventAllDamageToSourceEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public class GideonChampionOfJustice extends CardImpl {

    public GideonChampionOfJustice(UUID ownerId) {
        super(ownerId, 13, "Gideon, Champion of Justice", Rarity.MYTHIC, new CardType[]{CardType.PLANESWALKER}, "{2}{W}{W}");
        this.expansionSetCode = "GTC";
        this.subtype.add("Gideon");

        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(4));

        // +1: Put a loyalty counter on Gideon, Champion of Justice for each creature target opponent controls.
        LoyaltyAbility ability1 = new LoyaltyAbility(
                new AddCountersSourceEffect(CounterType.LOYALTY.createInstance(0), new PermanentsTargetOpponentControlsCount(new FilterCreaturePermanent()), true), 1);
        ability1.addTarget(new TargetOpponent());
        this.addAbility(ability1);

        // 0: Until end of turn, Gideon becomes an indestructible Human Soldier creature with power and toughness each equal to the number of loyalty counters on him. He's still a planeswalker. Prevent all damage that would be dealt to him this turn.
        LoyaltyAbility ability2 = new LoyaltyAbility(new BecomesCreatureSourceEffect(new GideonChampionOfJusticeToken(), "planeswalker", Duration.EndOfTurn, false, false), 0);
        ability2.addEffect(new PreventAllDamageToSourceEffect(Duration.EndOfTurn));
        this.addAbility(ability2);

        // -15: Exile all other permanents.
        this.addAbility(new LoyaltyAbility(new GideonExileAllOtherPermanentsEffect(), -15));

    }

    public GideonChampionOfJustice(final GideonChampionOfJustice card) {
        super(card);
    }

    @Override
    public GideonChampionOfJustice copy() {
        return new GideonChampionOfJustice(this);
    }
}

class GideonExileAllOtherPermanentsEffect extends OneShotEffect {

    public GideonExileAllOtherPermanentsEffect() {
        super(Outcome.Exile);
        staticText = "Exile all other permanents";
    }

    public GideonExileAllOtherPermanentsEffect(final GideonExileAllOtherPermanentsEffect effect) {
        super(effect);
    }

    @Override
    public GideonExileAllOtherPermanentsEffect copy() {
        return new GideonExileAllOtherPermanentsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents()) {
            if (!permanent.getId().equals(source.getSourceId())) {
                permanent.moveToExile(null, null, source.getSourceId(), game);
            }
        }
        return true;
    }
}

class GideonChampionOfJusticeToken extends Token {

    public GideonChampionOfJusticeToken() {
        super("", "indestructible Human Soldier creature with power and toughness each equal to the number of loyalty counters on him");
        cardType.add(CardType.CREATURE);
        subtype.add("Human");
        subtype.add("Soldier");
        power = new MageInt(0);
        toughness = new MageInt(0);
        
        this.addAbility(IndestructibleAbility.getInstance());
        
        CountersCount loyaltyCount = new CountersCount(CounterType.LOYALTY);
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetPowerToughnessSourceEffect(loyaltyCount, Duration.WhileOnBattlefield)));
    }

}
