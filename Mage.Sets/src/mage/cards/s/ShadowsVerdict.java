package mage.cards.s;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShadowsVerdict extends CardImpl {

    public ShadowsVerdict(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}{B}");

        // Exile all creatures and all planeswalkers with converted mana cost 3 or less from the battlefield and all creature and planeswalker cards with converted mana cost 3 or less from all graveyards.
        this.getSpellAbility().addEffect(new ShadowsVerdictEffect());
    }

    private ShadowsVerdict(final ShadowsVerdict card) {
        super(card);
    }

    @Override
    public ShadowsVerdict copy() {
        return new ShadowsVerdict(this);
    }
}

class ShadowsVerdictEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterCreatureOrPlaneswalkerPermanent();
    private static final FilterCard filter2 = new FilterCard();
    private static final Predicate<MageObject> predicate
            = new ManaValuePredicate(ComparisonType.FEWER_THAN, 4);

    static {
        filter.add(predicate);
        filter2.add(predicate);
        filter2.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.PLANESWALKER.getPredicate()
        ));
    }

    ShadowsVerdictEffect() {
        super(Outcome.Benefit);
        staticText = "exile all creatures and planeswalkers with mana value 3 or less from the battlefield " +
                "and all creature and planeswalker cards with mana value 3 or less from all graveyards";
    }

    private ShadowsVerdictEffect(final ShadowsVerdictEffect effect) {
        super(effect);
    }

    @Override
    public ShadowsVerdictEffect copy() {
        return new ShadowsVerdictEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        game.getBattlefield()
                .getActivePermanents(
                        filter, source.getControllerId(),
                        source, game
                ).stream()
                .forEach(cards::add);
        game.getState()
                .getPlayersInRange(source.getControllerId(), game)
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .map(Player::getGraveyard)
                .map(g -> g.getCards(filter2, game))
                .flatMap(Collection::stream)
                .forEach(cards::add);
        return player.moveCards(cards, Zone.EXILED, source, game);
    }
}
