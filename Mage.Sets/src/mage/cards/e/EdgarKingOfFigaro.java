package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.ArtifactYouControlCount;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.hint.common.ArtifactYouControlHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.FlipCoinsEvent;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EdgarKingOfFigaro extends CardImpl {

    public EdgarKingOfFigaro(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // When Edgar enters, draw a card for each artifact you control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new DrawCardSourceControllerEffect(ArtifactYouControlCount.instance)
        ).addHint(ArtifactYouControlHint.instance));

        // Two-Headed Coin -- The first time you flip one or more coins each turn, those coins come up heads and you win those flips.
        this.addAbility(new SimpleStaticAbility(new EdgarKingOfFigaroEffect())
                .withFlavorWord("Two-Headed Coin"), new EdgarKingOfFigaroWatcher());
    }

    private EdgarKingOfFigaro(final EdgarKingOfFigaro card) {
        super(card);
    }

    @Override
    public EdgarKingOfFigaro copy() {
        return new EdgarKingOfFigaro(this);
    }
}

class EdgarKingOfFigaroEffect extends ReplacementEffectImpl {

    EdgarKingOfFigaroEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "the first time you flip one or more coins each turn, " +
                "those coins come up heads and you win those flips";
    }

    private EdgarKingOfFigaroEffect(final EdgarKingOfFigaroEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        ((FlipCoinsEvent) event).setHeadsAndWon(true);
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.FLIP_COINS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.isControlledBy(event.getPlayerId())
                && !EdgarKingOfFigaroWatcher.checkPlayer(game, source);
    }

    @Override
    public EdgarKingOfFigaroEffect copy() {
        return new EdgarKingOfFigaroEffect(this);
    }
}

class EdgarKingOfFigaroWatcher extends Watcher {

    private final Set<UUID> set = new HashSet<>();

    EdgarKingOfFigaroWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.COIN_FLIPPED) {
            set.add(event.getPlayerId());
        }
    }

    @Override
    public void reset() {
        super.reset();
        set.clear();
    }

    static boolean checkPlayer(Game game, Ability source) {
        return game
                .getState()
                .getWatcher(EdgarKingOfFigaroWatcher.class)
                .set
                .contains(source.getControllerId());
    }
}
