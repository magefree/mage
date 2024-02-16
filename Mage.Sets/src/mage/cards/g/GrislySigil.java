package mage.cards.g;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.CasualtyAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreatureOrPlaneswalker;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GrislySigil extends CardImpl {

    public GrislySigil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}");

        // Casualty 1
        this.addAbility(new CasualtyAbility(1));

        // Choose target creature or planeswalker. If it was dealt noncombat damage this turn, Grisly Sigil deals 3 damage to it and you gain 3 life. Otherwise, Grisly Sigil deals 1 damage to it and you gain 1 life.
        this.getSpellAbility().addEffect(new GrislySigilEffect());
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
        this.getSpellAbility().addWatcher(new GrislySigilWatcher());
    }

    private GrislySigil(final GrislySigil card) {
        super(card);
    }

    @Override
    public GrislySigil copy() {
        return new GrislySigil(this);
    }
}

class GrislySigilEffect extends OneShotEffect {

    GrislySigilEffect() {
        super(Outcome.Benefit);
        staticText = "choose target creature or planeswalker. If it was dealt noncombat damage this turn, {this} " +
                "deals 3 damage to it and you gain 3 life. Otherwise, {this} deals 1 damage to it and you gain 1 life";
    }

    private GrislySigilEffect(final GrislySigilEffect effect) {
        super(effect);
    }

    @Override
    public GrislySigilEffect copy() {
        return new GrislySigilEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        int amount = GrislySigilWatcher.checkPermanent(permanent, game) ? 3 : 1;
        permanent.damage(amount, source, game);
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.gainLife(amount, game, source);
        }
        return true;
    }
}

class GrislySigilWatcher extends Watcher {

    private final Set<MageObjectReference> morSet = new HashSet<>();

    GrislySigilWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGED_PERMANENT
                && !((DamagedEvent) event).isCombatDamage()) {
            morSet.add(new MageObjectReference(event.getTargetId(), game));
        }
    }

    @Override
    public void reset() {
        super.reset();
        morSet.clear();
    }

    static boolean checkPermanent(Permanent permanent, Game game) {
        return game
                .getState()
                .getWatcher(GrislySigilWatcher.class)
                .morSet
                .stream()
                .anyMatch(mor -> mor.refersTo(permanent, game));
    }
}
