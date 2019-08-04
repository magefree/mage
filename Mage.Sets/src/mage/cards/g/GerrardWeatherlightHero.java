package mage.cards.g;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;
import mage.watchers.Watcher;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class GerrardWeatherlightHero extends CardImpl {

    public GerrardWeatherlightHero(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // When Gerrard, Weatherlight Hero dies, exile it and return to the battlefield all artifact and creature cards in your graveyard that were put there from the battlefield this turn.
        Ability ability = new DiesTriggeredAbility(new ExileSourceEffect().setText("exile it"));
        ability.addEffect(new GerrardWeatherlightHeroEffect());
        this.addAbility(ability, new GerrardWeatherlightHeroWatcher());
    }

    private GerrardWeatherlightHero(final GerrardWeatherlightHero card) {
        super(card);
    }

    @Override
    public GerrardWeatherlightHero copy() {
        return new GerrardWeatherlightHero(this);
    }
}

class GerrardWeatherlightHeroEffect extends OneShotEffect {

    GerrardWeatherlightHeroEffect() {
        super(Outcome.Benefit);
        staticText = "and return to the battlefield all artifact and creature cards " +
                "in your graveyard that were put there from the battlefield this turn";
    }

    private GerrardWeatherlightHeroEffect(final GerrardWeatherlightHeroEffect effect) {
        super(effect);
    }

    @Override
    public GerrardWeatherlightHeroEffect copy() {
        return new GerrardWeatherlightHeroEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        GerrardWeatherlightHeroWatcher watcher = game.getState().getWatcher(GerrardWeatherlightHeroWatcher.class);
        if (player == null || watcher == null) {
            return false;
        }
        return player.moveCards(
                player.getGraveyard()
                        .getCards(game)
                        .stream()
                        .filter(card -> watcher.checkCard(card, game))
                        .collect(Collectors.toSet()),
                Zone.BATTLEFIELD, source, game
        );
    }
}

class GerrardWeatherlightHeroWatcher extends Watcher {

    private final List<MageObjectReference> cards = new ArrayList<>();

    GerrardWeatherlightHeroWatcher() {
        super(WatcherScope.GAME);
    }

    private GerrardWeatherlightHeroWatcher(final GerrardWeatherlightHeroWatcher watcher) {
        super(watcher);
        this.cards.addAll(watcher.cards);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE
                && ((ZoneChangeEvent) event).isDiesEvent()) {
            cards.add(new MageObjectReference(event.getTargetId(), game));
        }
    }

    boolean checkCard(Card card, Game game) {
        if (!card.isCreature() && !card.isArtifact()) {
            return false;
        }
        return cards.stream().anyMatch(mageObjectReference -> mageObjectReference.refersTo(card, game));
    }

    @Override
    public GerrardWeatherlightHeroWatcher copy() {
        return new GerrardWeatherlightHeroWatcher(this);
    }

    @Override
    public void reset() {
        super.reset();
        cards.clear();
    }
}
// don’t mourn for me. this is my destiny.
