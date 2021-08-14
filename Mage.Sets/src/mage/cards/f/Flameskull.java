package mage.cards.f;

import mage.MageInt;
import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.*;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.*;

/**
 * @author TheElk801
 */
public final class Flameskull extends CardImpl {

    public Flameskull(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{R}");

        this.subtype.add(SubType.SKELETON);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Flameskull can't block.
        this.addAbility(new CantBlockAbility());

        // Rejuvenation — When Flameskull dies, exile it. If you do, exile the top card of your library. Until the end of your next turn, you may play one of those cards.
        this.addAbility(new DiesSourceTriggeredAbility(new FlameskullEffect())
                .withFlavorWord("Rejuvenation"), new FlameskullWatcher());
    }

    private Flameskull(final Flameskull card) {
        super(card);
    }

    @Override
    public Flameskull copy() {
        return new Flameskull(this);
    }
}

class FlameskullEffect extends OneShotEffect {

    FlameskullEffect() {
        super(Outcome.Benefit);
        staticText = "exile it. If you do, exile the top card of your library. " +
                "Until the end of your next turn, you may play one of those cards";
    }

    private FlameskullEffect(final FlameskullEffect effect) {
        super(effect);
    }

    @Override
    public FlameskullEffect copy() {
        return new FlameskullEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObjectIfItStillExists(game);
        if (player == null || !(sourceObject instanceof Card)) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getFromTop(game));
        cards.add((Card) sourceObject);
        player.moveCards(cards, Zone.EXILED, source, game);
        game.addEffect(new FlameskullPlayEffect(cards, game), source);
        return true;
    }
}

class FlameskullPlayEffect extends AsThoughEffectImpl {

    private final Set<MageObjectReference> morSet = new HashSet<>();

    FlameskullPlayEffect(Cards cards, Game game) {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.UntilEndOfYourNextTurn, Outcome.Benefit);
        cards.stream()
                .map(uuid -> new MageObjectReference(uuid, game))
                .forEach(morSet::add);
    }

    private FlameskullPlayEffect(final FlameskullPlayEffect effect) {
        super(effect);
        this.morSet.addAll(effect.morSet);
    }

    @Override
    public FlameskullPlayEffect copy() {
        return new FlameskullPlayEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        UUID objectIdToCast = CardUtil.getMainCardId(game, sourceId);
        return source.isControlledBy(affectedControllerId)
                && morSet.stream().anyMatch(mor -> mor.refersTo(objectIdToCast, game))
                && FlameskullWatcher.checkRef(source, morSet, game);
    }
}

class FlameskullWatcher extends Watcher {

    private final Map<MageObjectReference, Set<MageObjectReference>> morMap = new HashMap<>();
    private static final Set<MageObjectReference> emptySet = new HashSet<>();

    FlameskullWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.SPELL_CAST
                || event.getAdditionalReference() == null) {
            return;
        }
        MageObjectReference mor = event.getAdditionalReference().getApprovingMageObjectReference();
        Spell spell = game.getSpell(event.getTargetId());
        if (mor == null || spell == null) {
            return;
        }
        morMap.computeIfAbsent(mor, x -> new HashSet<>())
                .add(new MageObjectReference(spell.getMainCard(), game, -1));
    }

    static boolean checkRef(Ability source, Set<MageObjectReference> morSet, Game game) {
        FlameskullWatcher watcher = game.getState().getWatcher(FlameskullWatcher.class);
        return watcher != null
                && watcher
                .morMap
                .getOrDefault(new MageObjectReference(source.getSourceObject(game), game), emptySet)
                .stream()
                .noneMatch(morSet::contains);
    }
}
