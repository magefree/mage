
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlaneswalkerEntersWithLoyaltyCountersAbility;
import mage.abilities.dynamicvalue.LockedInDynamicValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.dynamicvalue.common.PermanentsTargetOpponentControlsCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PreventAllDamageToSourceEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TokenImpl;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public final class GideonChampionOfJustice extends CardImpl {

    public GideonChampionOfJustice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.PLANESWALKER},"{2}{W}{W}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.GIDEON);

        this.addAbility(new PlaneswalkerEntersWithLoyaltyCountersAbility(4));

        // +1: Put a loyalty counter on Gideon, Champion of Justice for each creature target opponent controls.
        LoyaltyAbility ability1 = new LoyaltyAbility(
                new AddCountersSourceEffect(CounterType.LOYALTY.createInstance(0), new PermanentsTargetOpponentControlsCount(new FilterCreaturePermanent()), true), 1);
        ability1.addTarget(new TargetOpponent());
        this.addAbility(ability1);

        // 0: Until end of turn, Gideon becomes an indestructible Human Soldier creature with power and toughness each equal to the number of loyalty counters on him. He's still a planeswalker. Prevent all damage that would be dealt to him this turn.
        LockedInDynamicValue loyaltyCount = new LockedInDynamicValue(new CountersSourceCount(CounterType.LOYALTY));
        LoyaltyAbility ability2 = new LoyaltyAbility(new BecomesCreatureSourceEffect(
                new GideonChampionOfJusticeToken(), "planeswalker", Duration.EndOfTurn, false, false, loyaltyCount, loyaltyCount), 0);
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

class GideonChampionOfJusticeToken extends TokenImpl {

    public GideonChampionOfJusticeToken() {
        super("", "indestructible Human Soldier creature with power and toughness each equal to the number of loyalty counters on him");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.HUMAN);
        subtype.add(SubType.SOLDIER);
        power = new MageInt(0);
        toughness = new MageInt(0);

        this.addAbility(IndestructibleAbility.getInstance());

    }
    public GideonChampionOfJusticeToken(final GideonChampionOfJusticeToken token) {
        super(token);
    }

    public GideonChampionOfJusticeToken copy() {
        return new GideonChampionOfJusticeToken(this);
    }
}
