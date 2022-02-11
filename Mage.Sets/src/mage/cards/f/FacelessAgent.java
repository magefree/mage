package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ChangelingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SubTypeSet;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class FacelessAgent extends CardImpl {

    public FacelessAgent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}");

        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Changeling
        this.addAbility(new ChangelingAbility());

        // When Faceless Agent enters the battlefield, seek a creature card of the most prevalent creature type in your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new FacelessAgentEffect()));
    }

    private FacelessAgent(final FacelessAgent card) {
        super(card);
    }

    @Override
    public FacelessAgent copy() {
        return new FacelessAgent(this);
    }
}

class FacelessAgentEffect extends OneShotEffect {

    private static final FilterCard filterAnyType = new FilterCreatureCard();

    static {
        filterAnyType.add(FacelessAgentPredicate.instance);
    }

    FacelessAgentEffect() {
        super(Outcome.Benefit);
        staticText = "seek a creature card of the most prevalent creature type in your library";
    }

    private FacelessAgentEffect(final FacelessAgentEffect effect) {
        super(effect);
    }

    @Override
    public FacelessAgentEffect copy() {
        return new FacelessAgentEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || player.getLibrary().count(filterAnyType, game) < 1) {
            return false;
        }
        Map<SubType, Integer> typeMap = player
                .getLibrary()
                .getCards(game)
                .stream()
                .filter(card -> !card.isAllCreatureTypes(game))
                .map(card -> card.getSubtype(game))
                .flatMap(Collection::stream)
                .filter(subType -> subType.getSubTypeSet() == SubTypeSet.CreatureType)
                .collect(Collectors.toMap(Function.identity(), x -> 1, Integer::sum));
        if (typeMap.isEmpty()) {
            return player.seekCard(filterAnyType, source, game);
        }
        int max = typeMap.values().stream().mapToInt(x -> x).max().orElse(0);
        FilterCard filter = new FilterCreatureCard();
        filter.add(Predicates.or(
                typeMap.entrySet()
                        .stream()
                        .filter(entry -> entry.getValue() == max)
                        .map(Map.Entry::getKey)
                        .map(SubType::getPredicate)
                        .collect(Collectors.toSet())
        ));
        return player.seekCard(filter, source, game);
    }
}

enum FacelessAgentPredicate implements Predicate<Card> {
    instance;

    @Override
    public boolean apply(Card input, Game game) {
        return input.isAllCreatureTypes(game)
                || input
                .getSubtype(game)
                .stream()
                .anyMatch(subType -> subType.getSubTypeSet() == SubTypeSet.CreatureType);
    }
}