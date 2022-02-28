package mage.cards.s;

import mage.MageObjectReference;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.WatcherScope;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.Watcher;

import java.util.*;

/**
 * @author TheElk801
 */
public final class SoulReap extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nongreen creature");

    static {
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.GREEN)));
    }

    public SoulReap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Destroy target nongreen creature. Its controller loses 3 life if you've cast another black spell this turn.
        this.getSpellAbility().addEffect(new SoulReapEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
        this.getSpellAbility().addWatcher(new SoulReapWatcher());
    }

    private SoulReap(final SoulReap card) {
        super(card);
    }

    @Override
    public SoulReap copy() {
        return new SoulReap(this);
    }
}

class SoulReapEffect extends OneShotEffect {

    public SoulReapEffect() {
        super(Outcome.Detriment);
        this.staticText = "destroy target nongreen creature. Its controller " +
                "loses 3 life if you've cast another black spell this turn";
    }

    public SoulReapEffect(final SoulReapEffect effect) {
        super(effect);
    }

    @Override
    public SoulReapEffect copy() {
        return new SoulReapEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (creature == null) {
            return false;
        }
        creature.destroy(source, game);
        if (!SoulReapWatcher.checkSpell(source, game)) {
            return true;
        }
        Player controller = game.getPlayer(creature.getControllerId());
        if (controller != null) {
            controller.loseLife(3, game, source, false);
        }
        return true;
    }
}

class SoulReapWatcher extends Watcher {

    private final Map<UUID, List<MageObjectReference>> spellMap = new HashMap<>();
    private static final List<MageObjectReference> emptyList = new ArrayList<>();

    SoulReapWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != EventType.SPELL_CAST) {
            return;
        }
        Spell spell = game.getSpell(event.getSourceId());
        if (spell != null && spell.getColor(game).isBlack()) {
            spellMap.computeIfAbsent(event.getPlayerId(), x -> new ArrayList<>())
                    .add(new MageObjectReference(event.getSourceId(), game));
        }
    }

    @Override
    public void reset() {
        super.reset();
        spellMap.clear();
    }

    static boolean checkSpell(Ability source, Game game) {
        return game.getState()
                .getWatcher(SoulReapWatcher.class)
                .spellMap
                .getOrDefault(source.getControllerId(), emptyList)
                .stream()
                .anyMatch(mor -> !mor.refersTo(source, game));
    }
}
