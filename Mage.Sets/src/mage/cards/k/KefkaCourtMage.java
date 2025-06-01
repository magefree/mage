package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.Controllable;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetDiscard;

import java.util.*;

/**
 * @author TheElk801
 */
public final class KefkaCourtMage extends CardImpl {

    public KefkaCourtMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);
        this.secondSideCardClazz = mage.cards.k.KefkaRulerOfRuin.class;

        // Whenever Kefka enters or attacks, each player discards a card. Then you draw a card for each card type among cards discarded this way.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(new KefkaCourtMageEffect()));

        // {8}: Each opponent sacrifices a permanent of their choice. Transform Kefka. Activate only as a sorcery.
        this.addAbility(new TransformAbility());
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new SacrificeOpponentsEffect(StaticFilters.FILTER_PERMANENT), new GenericManaCost(8)
        );
        ability.addEffect(new TransformSourceEffect());
        this.addAbility(ability);
    }

    private KefkaCourtMage(final KefkaCourtMage card) {
        super(card);
    }

    @Override
    public KefkaCourtMage copy() {
        return new KefkaCourtMage(this);
    }
}

class KefkaCourtMageEffect extends OneShotEffect {

    KefkaCourtMageEffect() {
        super(Outcome.Benefit);
        staticText = "each player discards a card. Then you draw a card " +
                "for each card type among cards discarded this way";
    }

    private KefkaCourtMageEffect(final KefkaCourtMageEffect effect) {
        super(effect);
    }

    @Override
    public KefkaCourtMageEffect copy() {
        return new KefkaCourtMageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Map<UUID, Card> map = new HashMap<>();
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            switch (player.getHand().size()) {
                case 0:
                    continue;
                case 1:
                    map.put(playerId, player.getHand().getRandom(game));
                    continue;
                default:
                    TargetDiscard target = new TargetDiscard(playerId);
                    player.choose(outcome, player.getHand(), target, source, game);
                    map.put(playerId, game.getCard(target.getFirstTarget()));
            }
        }
        Cards cards = new CardsImpl();
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            Card card = map.getOrDefault(playerId, null);
            if (player != null && card != null && player.discard(card, false, source, game)) {
                cards.add(card);
            }
        }
        if (cards.isEmpty()) {
            return false;
        }
        int count = cards.getCards(game)
                .stream()
                .map(card -> card.getCardType(game))
                .flatMap(Collection::stream)
                .distinct()
                .mapToInt(x -> 1)
                .sum();
        Optional.ofNullable(source)
                .map(Controllable::getControllerId)
                .map(game::getPlayer)
                .ifPresent(player -> player.drawCards(count, source, game));
        return true;
    }
}
