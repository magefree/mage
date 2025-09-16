package mage.cards.u;

import java.util.UUID;

import mage.MageIdentifier;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.common.counter.AddCounterEnteringCreatureEffect;
import mage.cards.Card;
import mage.constants.*;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.stack.Spell;
import mage.watchers.Watcher;

/**
 *
 * @author anonymous
 */
public final class UndeadSprinter extends CardImpl {

    public UndeadSprinter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{R}");
        
        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // You may cast this card from your graveyard if a non-Zombie creature died this turn. If you do, this creature enters with a +1/+1 counter on it.
        Ability ability = new SimpleStaticAbility(Zone.ALL, new UndeadSprinterEffect()).setIdentifier(MageIdentifier.UndeadSprinterAlternateCast);
        ability.addWatcher(new UndeadSprinterCanCastWatcher());
        ability.addWatcher(new UndeadSprinterAlternateCastWatcher());
        this.addAbility(ability);
    }

    private UndeadSprinter(final UndeadSprinter card) {
        super(card);
    }

    @Override
    public UndeadSprinter copy() {
        return new UndeadSprinter(this);
    }
}

class UndeadSprinterEffect extends AsThoughEffectImpl {

    UndeadSprinterEffect() {
        super(AsThoughEffectType.CAST_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfGame, Outcome.Benefit);
        this.staticText = "You may cast this card from your graveyard if a non-Zombie creature died this turn";
    }

    private UndeadSprinterEffect(final UndeadSprinterEffect effect) {
        super(effect);
    }

    @Override
    public UndeadSprinterEffect copy() {
        return new UndeadSprinterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        if (sourceId.equals(source.getSourceId()) && source.isControlledBy(affectedControllerId)) {
            Card card = game.getCard(source.getSourceId());
            Watcher watcher = game.getState().getWatcher(UndeadSprinterCanCastWatcher.class);
            if (card != null && watcher != null && game.getState().getZone(source.getSourceId()) == Zone.GRAVEYARD) {
                return watcher.conditionMet();
            }
        }
        return false;
    }
}

class UndeadSprinterCanCastWatcher extends Watcher {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("a non-Zombie creature");

    static {
        filter.add(Predicates.not(SubType.ZOMBIE.getPredicate()));
    }

    public UndeadSprinterCanCastWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (condition) {
            return;
        }
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE
                && ((ZoneChangeEvent) event).isDiesEvent()
                && filter.match(((ZoneChangeEvent) event).getTarget(), game)) {
            condition = true;
        }
    }
}

class UndeadSprinterAlternateCastWatcher extends Watcher {

    UndeadSprinterAlternateCastWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (GameEvent.EventType.SPELL_CAST.equals(event.getType())
                && event.hasApprovingIdentifier(MageIdentifier.UndeadSprinterAlternateCast)) {
            Spell target = game.getSpell(event.getTargetId());
            if (target != null) {
                game.getState().addEffect(new AddCounterEnteringCreatureEffect(new MageObjectReference(target.getCard(), game),
                                CounterType.P1P1.createInstance(), Outcome.BoostCreature),
                        target.getSpellAbility());
            }
        }
    }
}
