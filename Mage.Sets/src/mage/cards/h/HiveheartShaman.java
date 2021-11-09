package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.DomainValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.hint.common.DomainHint;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.InsectToken;
import mage.game.permanent.token.Token;
import mage.target.common.TargetCardInLibrary;

import java.util.Collection;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HiveheartShaman extends CardImpl {

    private static final FilterCard filter = new FilterCard(
            "basic land card that doesn't share a land type with a land you control"
    );

    static {
        filter.add(HiveheartShamanPredicate.instance);
    }

    public HiveheartShaman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Whenever Hiveheart Shaman attacks, you may search your library for a basic land card that doesn't share a land type with a land you control, put that card onto the battlefield, then shuffle.
        this.addAbility(new AttacksTriggeredAbility(new SearchLibraryPutInHandEffect(
                new TargetCardInLibrary(filter), true, true
        )));

        // {5}{G}: Create a 1/1 green Insect creature token. Put X +1/+1 counters on it, where X is the number of basic land types among lands you control. Activate only as a sorcery.
        this.addAbility(new SimpleActivatedAbility(
                new HiveheartShamanEffect(), new ManaCostsImpl<>("{5}{G}")
        ).addHint(DomainHint.instance));
    }

    private HiveheartShaman(final HiveheartShaman card) {
        super(card);
    }

    @Override
    public HiveheartShaman copy() {
        return new HiveheartShaman(this);
    }
}

enum HiveheartShamanPredicate implements ObjectSourcePlayerPredicate<Card> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        if (!input.getObject().isBasic() || !input.getObject().isLand(game)) {
            return false;
        }
        return game.getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND,
                        input.getPlayerId(), input.getSourceId(), game
                )
                .stream()
                .map(permanent -> permanent.getSubtype(game))
                .flatMap(Collection::stream)
                .filter(subType -> subType.getSubTypeSet().isLand())
                .noneMatch(subType -> input.getObject().hasSubtype(subType, game));
    }
}

class HiveheartShamanEffect extends OneShotEffect {

    private static final DynamicValue xValue = new DomainValue();

    HiveheartShamanEffect() {
        super(Outcome.Benefit);
        staticText = "create a 1/1 green Insect creature token. Put X +1/+1 counters on it, " +
                "where X is the number of basic land types among lands you control";
    }

    private HiveheartShamanEffect(final HiveheartShamanEffect effect) {
        super(effect);
    }

    @Override
    public HiveheartShamanEffect copy() {
        return new HiveheartShamanEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Token token = new InsectToken();
        token.putOntoBattlefield(1, game, source);
        int domainCount = xValue.calculate(game, source, this);
        if (domainCount < 1) {
            return true;
        }
        for (UUID tokenId : token.getLastAddedTokenIds()) {
            Permanent permanent = game.getPermanent(tokenId);
            if (permanent == null) {
                continue;
            }
            permanent.addCounters(CounterType.P1P1.createInstance(domainCount), source, game);
        }
        return true;
    }
}
