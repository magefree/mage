
package mage.cards.s;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.WatcherScope;
import mage.filter.FilterSpell;
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

/**
 *
 * @author jeffwadsworth
 */
public final class SoulReap extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nongreen creature");

    static {
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.GREEN)));
    }

    private static final String rule = "Its controller loses 3 life if you've cast another black spell this turn";

    public SoulReap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{B}");

        // Destroy target nongreen creature. Its controller loses 3 life if you've cast another black spell this turn.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(new SoulReapEffect(), new CastBlackSpellThisTurnCondition(), rule));
        this.getSpellAbility().addWatcher(new SoulReapWatcher(this.getId()));

    }

    private SoulReap(final SoulReap card) {
        super(card);
    }

    @Override
    public SoulReap copy() {
        return new SoulReap(this);
    }
}

class CastBlackSpellThisTurnCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        SoulReapWatcher watcher = game.getState().getWatcher(SoulReapWatcher.class, source.getControllerId());
        if (watcher != null) {
            return watcher.conditionMet();
        }
        return false;
    }
}

class SoulReapWatcher extends Watcher {

    private static final FilterSpell filter = new FilterSpell();

    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    private UUID cardId;

    public SoulReapWatcher(UUID cardId) {
        super(WatcherScope.PLAYER);
        this.cardId = cardId;
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (condition == true) { //no need to check - condition has already occured
            return;
        }
        if (event.getType() == GameEvent.EventType.SPELL_CAST
                && controllerId.equals(event.getPlayerId())) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (!spell.getSourceId().equals(cardId) && filter.match(spell, game)) {
                condition = true;
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        condition = false;
    }
}

class SoulReapEffect extends OneShotEffect {

    public SoulReapEffect() {
        super(Outcome.Detriment);
        this.staticText = "Its controller loses 3 life if you've cast another black spell this turn";
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
        Permanent creature = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (creature != null) {
            Player controller = game.getPlayer(creature.getControllerId());
            if (controller != null) {
                controller.loseLife(3, game, source, false);
                return true;
            }
        }
        return false;
    }
}
