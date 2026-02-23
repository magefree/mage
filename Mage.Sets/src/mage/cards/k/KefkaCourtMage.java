package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.LoseLifeTriggeredAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.SavedLifeLossValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.*;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Controllable;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetDiscard;

import java.util.*;

/**
 * @author TheElk801
 */
public final class KefkaCourtMage extends TransformingDoubleFacedCard {

    public KefkaCourtMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WIZARD}, "{2}{U}{B}{R}",
                "Kefka, Ruler of Ruin",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.AVATAR, SubType.WIZARD}, "UBR");

        // Kefka, Court Mage
        this.getLeftHalfCard().setPT(4, 5);

        // Whenever Kefka enters or attacks, each player discards a card. Then you draw a card for each card type among cards discarded this way.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(new KefkaCourtMageEffect()));

        // {8}: Each opponent sacrifices a permanent of their choice. Transform Kefka. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new SacrificeOpponentsEffect(StaticFilters.FILTER_PERMANENT), new GenericManaCost(8)
        );
        ability.addEffect(new TransformSourceEffect());
        this.getLeftHalfCard().addAbility(ability);

        // Kefka, Ruler of Ruin
        this.getRightHalfCard().setPT(5, 7);

        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());

        // Whenever an opponent loses life during your turn, you draw that many cards.
        this.getRightHalfCard().addAbility(new LoseLifeTriggeredAbility(
                new DrawCardSourceControllerEffect(SavedLifeLossValue.MANY, true),
                TargetController.OPPONENT, false, false
        ).withTriggerCondition(MyTurnCondition.instance));
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
