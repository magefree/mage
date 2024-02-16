package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetDiscard;
import mage.target.targetpointer.FixedTarget;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.filter.common.FilterControlledPermanent;

/**
 * @author TheElk801
 */
public final class BlimComedicGenius extends CardImpl {

    public BlimComedicGenius(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.IMP);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Blim, Comedic Genius deals combat damage to a player, that player gains control of target permanent you control. Then each player loses life and discards cards equal to the number of permanents they control but don't own.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(
                new BlimComedicGeniusEffect(), false, true
        );
        ability.addTarget(new TargetControlledPermanent());
        this.addAbility(ability);
    }

    private BlimComedicGenius(final BlimComedicGenius card) {
        super(card);
    }

    @Override
    public BlimComedicGenius copy() {
        return new BlimComedicGenius(this);
    }
}

class BlimComedicGeniusEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledPermanent();

    static {
        filter.add(TargetController.NOT_YOU.getOwnerPredicate());
    }

    BlimComedicGeniusEffect() {
        super(Outcome.Benefit);
        staticText = "that player gains control of target permanent you control. Then each player loses life "
                + "and discards cards equal to the number of permanents they control but don't own";
    }

    private BlimComedicGeniusEffect(final BlimComedicGeniusEffect effect) {
        super(effect);
    }

    @Override
    public BlimComedicGeniusEffect copy() {
        return new BlimComedicGeniusEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.addEffect(new GainControlTargetEffect(
                Duration.Custom, true, targetPointer.getFirst(game, source)
        ).setTargetPointer(new FixedTarget(source.getFirstTarget(), game)), source);
        game.getState().processAction(game);
        Map<UUID, Cards> cardsMap = new HashMap<>();
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            int count = game.getBattlefield().count(filter, playerId, source, game);
            if (count < 1) {
                continue;
            }
            player.loseLife(count, game, source, true);
            TargetDiscard target = new TargetDiscard(StaticFilters.FILTER_CARD, playerId);
            player.choose(outcome, target, source, game);
            cardsMap.put(playerId, new CardsImpl(target.getTargets()));
        }
        for (Map.Entry<UUID, Cards> entry : cardsMap.entrySet()) {
            Player player = game.getPlayer(entry.getKey());
            if (player == null) {
                continue;
            }
            player.discard(entry.getValue(), false, source, game);
        }
        return true;
    }
}
