package mage.cards.m;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.ForetellAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeGroupEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MysticReflection extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("nonlegendary creature");

    static {
        filter.add(Predicates.not(SuperType.LEGENDARY.getPredicate()));
    }

    public MysticReflection(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Choose target nonlegendary creature. The next time one or more creatures or planeswalkers enter the battlefield this turn, they enter as copies of the chosen creature instead.
        this.getSpellAbility().addEffect(new MysticReflectionEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addWatcher(new MysticReflectionWatcher());

        // Foretell {U}
        this.addAbility(new ForetellAbility(this, "{U}"));
    }

    private MysticReflection(final MysticReflection card) {
        super(card);
    }

    @Override
    public MysticReflection copy() {
        return new MysticReflection(this);
    }
}

class MysticReflectionEffect extends OneShotEffect {

    MysticReflectionEffect() {
        super(Outcome.Benefit);
        staticText = "Choose target nonlegendary creature. The next time one or more creatures or planeswalkers " +
                "enter the battlefield this turn, they enter as copies of the chosen creature instead.";
    }

    private MysticReflectionEffect(final MysticReflectionEffect effect) {
        super(effect);
    }

    @Override
    public MysticReflectionEffect copy() {
        return new MysticReflectionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        MysticReflectionWatcher watcher = game.getState().getWatcher(MysticReflectionWatcher.class);
        if (permanent == null || watcher == null) {
            return false;
        }
        game.addEffect(new MysticReflectionCopyEffect(permanent, watcher.getEnteredThisTurn()), source);
        return true;
    }
}

class MysticReflectionCopyEffect extends ReplacementEffectImpl {

    private final Permanent permanent;
    private final int enteredThisTurn;

    MysticReflectionCopyEffect(Permanent permanent, int enteredThisTurn) {
        super(Duration.Custom, Outcome.Copy, false);
        this.permanent = permanent;
        this.enteredThisTurn = enteredThisTurn;
    }

    private MysticReflectionCopyEffect(MysticReflectionCopyEffect effect) {
        super(effect);
        this.permanent = effect.permanent;
        this.enteredThisTurn = effect.enteredThisTurn;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (permanent == null) {
            discard();
            return false;
        }
        MysticReflectionWatcher watcher = game.getState().getWatcher(MysticReflectionWatcher.class);
        if (watcher != null && watcher.getEnteredThisTurn() > this.enteredThisTurn) {
            discard();
            return false;
        }
        Permanent perm = ((EntersTheBattlefieldEvent) event).getTarget();
        return perm != null
                && (perm.isCreature() || perm.isPlaneswalker())
                && perm.isControlledBy(source.getControllerId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        if (this.permanent != null) {
            game.copyPermanent(this.permanent, event.getTargetId(), source, null);
        }
        return false;
    }

    @Override
    public MysticReflectionCopyEffect copy() {
        return new MysticReflectionCopyEffect(this);
    }

}

class MysticReflectionWatcher extends Watcher {

    private int enteredThisTurn = 0;

    MysticReflectionWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ZONE_CHANGE_GROUP) {
            return;
        }
        ZoneChangeGroupEvent zEvent = (ZoneChangeGroupEvent) event;
        if (zEvent.getToZone() != Zone.BATTLEFIELD) {
            return;
        }
        Set<Card> cards = new HashSet<>();
        cards.addAll(zEvent.getCards());
        cards.addAll(zEvent.getTokens());
        if (cards.stream()
                .filter(Objects::nonNull)
                .map(MageItem::getId)
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .anyMatch(p -> p.isPlaneswalker() || p.isCreature())) {
            enteredThisTurn++;
        }
    }

    @Override
    public void reset() {
        super.reset();
        enteredThisTurn = 0;
    }

    public int getEnteredThisTurn() {
        return enteredThisTurn;
    }
}
