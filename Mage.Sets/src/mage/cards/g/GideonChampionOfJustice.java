package mage.cards.g;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
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
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.token.TokenImpl;
import mage.players.Player;
import mage.target.common.TargetOpponent;

import java.util.Objects;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class GideonChampionOfJustice extends CardImpl {

    public GideonChampionOfJustice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{W}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GIDEON);

        this.setStartingLoyalty(4);

        // +1: Put a loyalty counter on Gideon, Champion of Justice for each creature target opponent controls.
        LoyaltyAbility ability1 = new LoyaltyAbility(
                new AddCountersSourceEffect(CounterType.LOYALTY.createInstance(0), new PermanentsTargetOpponentControlsCount(new FilterCreaturePermanent()), true), 1);
        ability1.addTarget(new TargetOpponent());
        this.addAbility(ability1);

        // 0: Until end of turn, Gideon becomes an indestructible Human Soldier creature with power and toughness each equal to the number of loyalty counters on him. He's still a planeswalker. Prevent all damage that would be dealt to him this turn.
        LockedInDynamicValue loyaltyCount = new LockedInDynamicValue(new CountersSourceCount(CounterType.LOYALTY));
        LoyaltyAbility ability2 = new LoyaltyAbility(new BecomesCreatureSourceEffect(
                new GideonChampionOfJusticeToken(), CardType.PLANESWALKER, Duration.EndOfTurn).withDynamicPT(loyaltyCount, loyaltyCount)
                .setText("Until end of turn, {this} becomes a Human Soldier creature with power and toughness each equal to the number of loyalty counters on him and gains indestructible. He's still a planeswalker."), 0);
        ability2.addEffect(new PreventAllDamageToSourceEffect(Duration.EndOfTurn).setText("prevent all damage that would be dealt to him this turn"));
        this.addAbility(ability2);

        // -15: Exile all other permanents.
        this.addAbility(new LoyaltyAbility(new GideonExileAllOtherPermanentsEffect(), -15));
    }

    private GideonChampionOfJustice(final GideonChampionOfJustice card) {
        super(card);
    }

    @Override
    public GideonChampionOfJustice copy() {
        return new GideonChampionOfJustice(this);
    }
}

class GideonExileAllOtherPermanentsEffect extends OneShotEffect {

    GideonExileAllOtherPermanentsEffect() {
        super(Outcome.Exile);
        staticText = "exile all other permanents";
    }

    private GideonExileAllOtherPermanentsEffect(final GideonExileAllOtherPermanentsEffect effect) {
        super(effect);
    }

    @Override
    public GideonExileAllOtherPermanentsEffect copy() {
        return new GideonExileAllOtherPermanentsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        MageObjectReference mor = new MageObjectReference(source.getSourceObject(game), game);
        Cards cards = new CardsImpl();
        game.getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_PERMANENT,
                        source.getControllerId(), game
                ).stream()
                .filter(Objects::nonNull)
                .filter(permanent -> !mor.refersTo(permanent, game))
                .forEach(cards::add);
        return player.moveCards(cards, Zone.EXILED, source, game);
    }
}

class GideonChampionOfJusticeToken extends TokenImpl {

    GideonChampionOfJusticeToken() {
        super("", "indestructible Human Soldier creature with power and toughness each equal to the number of loyalty counters on him");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.HUMAN);
        subtype.add(SubType.SOLDIER);
        power = new MageInt(0);
        toughness = new MageInt(0);

        this.addAbility(IndestructibleAbility.getInstance());

    }

    private GideonChampionOfJusticeToken(final GideonChampionOfJusticeToken token) {
        super(token);
    }

    public GideonChampionOfJusticeToken copy() {
        return new GideonChampionOfJusticeToken(this);
    }
}
