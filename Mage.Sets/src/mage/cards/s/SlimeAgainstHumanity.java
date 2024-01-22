package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.OozeToken;
import mage.game.permanent.token.Token;
import mage.players.Player;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SlimeAgainstHumanity extends CardImpl {

    public SlimeAgainstHumanity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Create a 0/0 green Ooze creature token with trample. Put X +1/+1 counters on it, where X is two plus the total number of cards you own in exile and in your graveyard that are Oozes or are named Slime Against Humanity.
        this.getSpellAbility().addEffect(new SlimeAgainstHumanityEffect());

        // A deck can have any number of cards named Slime Against Humanity.
        this.getSpellAbility().addEffect(new InfoEffect("<br>A deck can have any number of cards named Slime Against Humanity"));
    }

    private SlimeAgainstHumanity(final SlimeAgainstHumanity card) {
        super(card);
    }

    @Override
    public SlimeAgainstHumanity copy() {
        return new SlimeAgainstHumanity(this);
    }
}

class SlimeAgainstHumanityEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(Predicates.or(
                SubType.OOZE.getPredicate(),
                new NamePredicate("Slime Against Humanity")
        ));
    }

    SlimeAgainstHumanityEffect() {
        super(Outcome.Benefit);
        staticText = "create a 0/0 green Ooze creature token with trample. Put X +1/+1 counters on it, " +
                "where X is two plus the total number of cards you own in exile and " +
                "in your graveyard that are Oozes or are named Slime Against Humanity";
    }

    private SlimeAgainstHumanityEffect(final SlimeAgainstHumanityEffect effect) {
        super(effect);
    }

    @Override
    public SlimeAgainstHumanityEffect copy() {
        return new SlimeAgainstHumanityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Token token = new OozeToken(0, 0);
        token.putOntoBattlefield(1, game, source);
        int graveCount = Optional
                .ofNullable(game.getPlayer(source.getControllerId()))
                .filter(Objects::nonNull)
                .map(Player::getGraveyard)
                .map(g -> g.count(filter, game))
                .orElse(0);
        int exileCount = game
                .getState()
                .getExile()
                .getAllCards(game, source.getControllerId())
                .stream()
                .filter(card -> filter.match(card, game))
                .mapToInt(x -> 1)
                .sum();
        int xValue = 2 + graveCount + exileCount;
        if (xValue < 1) {
            return true;
        }
        for (UUID tokenId : token.getLastAddedTokenIds()) {
            Permanent permanent = game.getPermanent(tokenId);
            if (permanent == null) {
                continue;
            }
            permanent.addCounters(CounterType.P1P1.createInstance(xValue), source, game);
        }
        return true;
    }
}
