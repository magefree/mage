package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.cards.Card;
import mage.constants.*;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.watchers.Watcher;

/**
 *
 * @author weirddan455
 */
public final class EbondeathDracolich extends CardImpl {

    public EbondeathDracolich(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Ebondeath, Dracolich enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // You may cast Ebondeath, Dracolich from your graveyard if a creature not named Ebondeath, Dracolich died this turn.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new EbondeathDracolichEffect()), new EbondeathDracolichWatcher());
    }

    private EbondeathDracolich(final EbondeathDracolich card) {
        super(card);
    }

    @Override
    public EbondeathDracolich copy() {
        return new EbondeathDracolich(this);
    }
}

class EbondeathDracolichEffect extends AsThoughEffectImpl {

    public EbondeathDracolichEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfGame, Outcome.Benefit);
        this.staticText = "You may cast {this} from your graveyard if a creature not named Ebondeath, Dracolich died this turn";
    }

    private EbondeathDracolichEffect(final EbondeathDracolichEffect effect) {
        super(effect);
    }

    @Override
    public EbondeathDracolichEffect copy() {
        return new EbondeathDracolichEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        if (sourceId.equals(source.getSourceId()) && source.isControlledBy(affectedControllerId)) {
            Card card = game.getCard(source.getSourceId());
            Watcher watcher = game.getState().getWatcher(EbondeathDracolichWatcher.class);
            if (card != null && watcher != null && game.getState().getZone(source.getSourceId()) == Zone.GRAVEYARD) {
                return watcher.conditionMet();
            }
        }
        return false;
    }
}

class EbondeathDracolichWatcher extends Watcher {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("a creature not named Ebondeath, Dracolich");

    static {
        filter.add(Predicates.not(new NamePredicate("Ebondeath, Dracolich")));
    }

    public EbondeathDracolichWatcher() {
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
