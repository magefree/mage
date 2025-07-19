package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.DiscardsACardOpponentTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.StationAbility;
import mage.abilities.keyword.StationLevelAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetDiscard;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EntropicBattlecruiser extends CardImpl {

    public EntropicBattlecruiser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}{B}");

        this.subtype.add(SubType.SPACECRAFT);

        // Station
        this.addAbility(new StationAbility());

        // STATION 1+
        // Whenever an opponent discards a card, they lose 3 life.
        this.addAbility(new StationLevelAbility(1).withLevelAbility(new DiscardsACardOpponentTriggeredAbility(
                new LoseLifeTargetEffect(3).setText("they lose 3 life"), false
        )));

        // STATION 8+
        // Flying
        // Deathtouch
        // Whenever this Spacecraft attacks, each opponent discards a card. Each opponent who doesn't loses 3 life.
        // 3/10
        this.addAbility(new StationLevelAbility(8)
                .withLevelAbility(FlyingAbility.getInstance())
                .withLevelAbility(DeathtouchAbility.getInstance())
                .withLevelAbility(new AttacksTriggeredAbility(new EntropicBattlecruiserEffect()))
                .withPT(3, 10));
    }

    private EntropicBattlecruiser(final EntropicBattlecruiser card) {
        super(card);
    }

    @Override
    public EntropicBattlecruiser copy() {
        return new EntropicBattlecruiser(this);
    }
}

class EntropicBattlecruiserEffect extends OneShotEffect {

    EntropicBattlecruiserEffect() {
        super(Outcome.Benefit);
        staticText = "each opponent discards a card. Each opponent who doesn't loses 3 life";
    }

    private EntropicBattlecruiserEffect(final EntropicBattlecruiserEffect effect) {
        super(effect);
    }

    @Override
    public EntropicBattlecruiserEffect copy() {
        return new EntropicBattlecruiserEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Map<UUID, Card> map = new HashMap<>();
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            Player player = game.getPlayer(opponentId);
            if (player == null || player.getHand().isEmpty()) {
                continue;
            }
            TargetDiscard target = new TargetDiscard(opponentId);
            player.choose(Outcome.Discard, target, source, game);
            map.put(opponentId, game.getCard(target.getFirstTarget()));
        }
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            Player player = game.getPlayer(opponentId);
            if (player != null && !player.discard(
                    map.getOrDefault(opponentId, null), false, source, game
            )) {
                player.loseLife(3, game, source, false);
            }
        }
        return true;
    }
}
