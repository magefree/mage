package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.CanBeYourCommanderAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.*;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.predicate.card.OwnerIdPredicate;
import mage.game.Game;
import mage.game.permanent.token.Demon33Token;
import mage.players.Player;
import mage.target.common.TargetCardInOpponentsGraveyard;
import mage.target.targetadjustment.TargetAdjuster;
import mage.target.targetpointer.EachTargetPointer;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TashaTheWitchQueen extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a spell you don't own");

    static {
        filter.add(TargetController.NOT_YOU.getOwnerPredicate());
    }

    public TashaTheWitchQueen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{U}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.TASHA);
        this.setStartingLoyalty(4);

        // Whenever you cast a spell you don't own, create a 3/3 black Demon creature token.
        this.addAbility(new SpellCastControllerTriggeredAbility(new CreateTokenEffect(new Demon33Token()), filter, false));

        // +1: Draw a card. For each opponent, exile up to one target instant or sorcery card from that player's graveyard and put a page counter on it.
        Ability ability = new LoyaltyAbility(new DrawCardSourceControllerEffect(1), 1);
        ability.addEffect(new TashaTheWitchQueenExileEffect());
        ability.setTargetAdjuster(TashaTheWitchQueenAdjuster.instance);
        this.addAbility(ability);

        // −3: You may cast a spell from among cards in exile with page counters on them without paying its mana cost.
        this.addAbility(new LoyaltyAbility(new TashaTheWitchQueenCastEffect(), -3));

        // Tasha, the Witch Queen can be your commander.
        this.addAbility(CanBeYourCommanderAbility.getInstance());
    }

    private TashaTheWitchQueen(final TashaTheWitchQueen card) {
        super(card);
    }

    @Override
    public TashaTheWitchQueen copy() {
        return new TashaTheWitchQueen(this);
    }
}

enum TashaTheWitchQueenAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        for (UUID opponentId : game.getOpponents(ability.getControllerId())) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent == null) {
                continue;
            }
            FilterCard filter = new FilterInstantOrSorceryCard("instant or sorcery card from " + opponent.getLogName() + "'s graveyard");
            filter.add(new OwnerIdPredicate(opponentId));
            TargetCardInOpponentsGraveyard target = new TargetCardInOpponentsGraveyard(0, 1, filter);
            ability.addTarget(target);
        }
    }
}

class TashaTheWitchQueenExileEffect extends OneShotEffect {

    TashaTheWitchQueenExileEffect() {
        super(Outcome.Benefit);
        this.setTargetPointer(new EachTargetPointer());
        staticText = "For each opponent, exile up to one target instant or sorcery card " +
                "from that player's graveyard and put a page counter on it";
    }

    private TashaTheWitchQueenExileEffect(final TashaTheWitchQueenExileEffect effect) {
        super(effect);
    }

    @Override
    public TashaTheWitchQueenExileEffect copy() {
        return new TashaTheWitchQueenExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(getTargetPointer().getTargets(game, source));
        player.moveCards(cards, Zone.EXILED, source, game);
        cards.retainZone(Zone.EXILED, game);
        for (Card card : cards.getCards(game)) {
            card.addCounters(CounterType.PAGE.createInstance(), source, game);
        }
        return true;
    }
}

class TashaTheWitchQueenCastEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(CounterType.PAGE.getPredicate());
    }

    TashaTheWitchQueenCastEffect() {
        super(Outcome.Benefit);
        staticText = "you may cast a spell from among cards in exile with page counters on them without paying its mana cost";
    }

    private TashaTheWitchQueenCastEffect(final TashaTheWitchQueenCastEffect effect) {
        super(effect);
    }

    @Override
    public TashaTheWitchQueenCastEffect copy() {
        return new TashaTheWitchQueenCastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(game.getExile().getCards(filter, game));
        return !cards.isEmpty() && CardUtil.castSpellWithAttributesForFree(player, source, game, cards, StaticFilters.FILTER_CARD);
    }
}
