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
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PreventAllDamageSourceEffect;
import mage.abilities.effects.common.continious.BecomesCreatureSourceEffect;
import mage.abilities.effects.common.continious.SetPowerToughnessSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public class GideonChampionOfJustice extends CardImpl<GideonChampionOfJustice> {

    public GideonChampionOfJustice(UUID ownerId) {
        super(ownerId, 13, "Gideon, Champion of Justice", Rarity.MYTHIC, new CardType[]{CardType.PLANESWALKER}, "{2}{W}{W}");
        this.expansionSetCode = "GTC";
        this.subtype.add("Gideon");
        this.color.setWhite(true);
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.LOYALTY.createInstance(4)), false));

        // +1: Put a loyalty counter on Gideon, Champion of Justice for each creature target opponent controls.
        LoyaltyAbility ability1 = new LoyaltyAbility(
                new AddCountersSourceEffect(CounterType.LOYALTY.createInstance(0), new PermanentsTargetOpponentControlsCount(), false), 1);
        ability1.addTarget(new TargetOpponent());
        this.addAbility(ability1);

        // 0: Until end of turn, Gideon becomes an indestructible Human Soldier creature with power and toughness each equal to the number of loyalty counters on him. He's still a planeswalker. Prevent all damage that would be dealt to him this turn.
        LoyaltyAbility ability2 = new LoyaltyAbility(new BecomesCreatureSourceEffect(new GideonChampionOfJusticeToken(), "planeswalker", Constants.Duration.EndOfTurn), 0);
        ability2.addEffect(new PreventAllDamageSourceEffect(Constants.Duration.EndOfTurn));
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

class PermanentsTargetOpponentControlsCount implements DynamicValue {

    public PermanentsTargetOpponentControlsCount() {
    }

    @Override
    public int calculate(Game game, Ability sourceAbility) {
        if (sourceAbility.getFirstTarget() != null) {
            FilterCreaturePermanent filter = new FilterCreaturePermanent();
            filter.add(new ControllerIdPredicate(sourceAbility.getFirstTarget()));
            int value = game.getBattlefield().count(filter, sourceAbility.getSourceId(), sourceAbility.getControllerId(), game);
            return value;
        } else {
            return 0;
        }
    }

    @Override
    public DynamicValue copy() {
        return new PermanentsTargetOpponentControlsCount();
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "creature target opponent controls";
    }
}

class GideonExileAllOtherPermanentsEffect extends OneShotEffect<GideonExileAllOtherPermanentsEffect> {

    public GideonExileAllOtherPermanentsEffect() {
        super(Constants.Outcome.Exile);
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

        this.addAbility(new IndestructibleAbility());
        this.addAbility(new SimpleStaticAbility(Constants.Zone.ALL, new SetPowerToughnessSourceEffect(new CountersCount(CounterType.LOYALTY), Constants.Duration.WhileOnBattlefield)));
    }

}
