package mage.cards.u;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
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
public final class UnluckyWitness extends CardImpl {

    public UnluckyWitness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Unlucky Witness dies, exile the top two cards of your library. Until your next end step, you may play one of those cards.
        this.addAbility(new DiesSourceTriggeredAbility(new UnluckyWitnessEffect()));
    }

    private UnluckyWitness(final UnluckyWitness card) {
        super(card);
    }

    @Override
    public UnluckyWitness copy() {
        return new UnluckyWitness(this);
    }
}

class UnluckyWitnessEffect extends OneShotEffect {

    UnluckyWitnessEffect() {
        super(Outcome.Benefit);
        staticText = "exile the top two cards of your library. " +
                "Until your next end step, you may play one of those cards";
    }

    private UnluckyWitnessEffect(final UnluckyWitnessEffect effect) {
        super(effect);
    }

    @Override
    public UnluckyWitnessEffect copy() {
        return new UnluckyWitnessEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 2));
        player.moveCards(cards, Zone.EXILED, source, game);
        game.addEffect(new UnluckyWitnessPlayEffect(cards, game), source);
        return true;
    }
}

class UnluckyWitnessPlayEffect extends AsThoughEffectImpl {

    private final Set<MageObjectReference> morSet = new HashSet<>();

    UnluckyWitnessPlayEffect(Cards cards, Game game) {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.UntilYourNextEndStep, Outcome.Benefit);
        cards.stream()
                .map(uuid -> new MageObjectReference(uuid, game))
                .forEach(morSet::add);
    }

    private UnluckyWitnessPlayEffect(final UnluckyWitnessPlayEffect effect) {
        super(effect);
        this.morSet.addAll(effect.morSet);
    }

    @Override
    public UnluckyWitnessPlayEffect copy() {
        return new UnluckyWitnessPlayEffect(this);
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
                && UnluckyWitnessWatcher.checkRef(source, morSet, game);
    }
}

class UnluckyWitnessWatcher extends Watcher {

    private final Map<MageObjectReference, Set<MageObjectReference>> morMap = new HashMap<>();
    private static final Set<MageObjectReference> emptySet = new HashSet<>();

    UnluckyWitnessWatcher() {
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
        UnluckyWitnessWatcher watcher = game.getState().getWatcher(UnluckyWitnessWatcher.class);
        return watcher != null
                && watcher
                .morMap
                .getOrDefault(new MageObjectReference(source.getSourceObject(game), game), emptySet)
                .stream()
                .noneMatch(morSet::contains);
    }
}
