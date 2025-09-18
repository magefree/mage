package mage.cards.t;

import java.util.*;
import mage.MageIdentifier;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.hint.Hint;
import mage.abilities.hint.common.ConditionPermanentHint;
import mage.abilities.effects.common.continuous.PlayFromTopOfLibraryEffect;
import mage.abilities.effects.common.continuous.LookAtTopCardOfLibraryAnyTimeEffect;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.HistoricPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.FoodToken;
import mage.players.Player;
import mage.watchers.Watcher;


/**
 * @author padfoothelix
 */
public final class TheFourthDoctor extends CardImpl {

    public TheFourthDoctor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{U}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TIME_LORD);
        this.subtype.add(SubType.DOCTOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // You may look at the top card of your library any time.
        this.addAbility(new SimpleStaticAbility(new LookAtTopCardOfLibraryAnyTimeEffect()));
	
	// Would You Like A...? -- Once each turn, you may play a historic land or cast a historic spell from the top of your library. When you do, create a Food token.
        this.addAbility(
                new SimpleStaticAbility(
                        new TheFourthDoctorPlayFromTopEffect())
                        .setIdentifier(MageIdentifier.TheFourthDoctorWatcher)
                        .addHint(new ConditionPermanentHint(
                                TheFourthDoctorCondition.instance,
                                "You may play a historic land or cast a historic spell from the top of your library.",
                                null,
                                "You have already played a historic land or cast a historic spell from the top of your library this turn",
                                null,
                                true
                        ))
                        .withFlavorWord("Would You Like A...?"),
                new TheFourthDoctorWatcher()
        );
        this.addAbility(new TheFourthDoctorTriggeredAbility());
    }

    private TheFourthDoctor(final TheFourthDoctor card) {
        super(card);
    }

    @Override
    public TheFourthDoctor copy() {
        return new TheFourthDoctor(this);
    }
}

class TheFourthDoctorPlayFromTopEffect extends PlayFromTopOfLibraryEffect {

    private static final FilterCard filter = new FilterCard("");

    static {
        filter.add(HistoricPredicate.instance);
    }

    TheFourthDoctorPlayFromTopEffect() {
        super(filter);
        staticText = "Once each turn, you may play a historic land or cast a historic spell from the top of your library. " +
            "When you do, create a Food token.";
    }

    private TheFourthDoctorPlayFromTopEffect(final TheFourthDoctorPlayFromTopEffect effect) {
        super(effect);
    }

    @Override
    public TheFourthDoctorPlayFromTopEffect copy() {
        return new TheFourthDoctorPlayFromTopEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability affectedAbility, Ability source, Game game, UUID playerId) {
       
        if (!super.applies(objectId, affectedAbility, source, game, playerId)) {
            return false;
        }

        Player controller = game.getPlayer(source.getControllerId());
        TheFourthDoctorWatcher watcher = game.getState().getWatcher(TheFourthDoctorWatcher.class);
        Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
        
        return controller != null
		        && watcher != null
                && !watcher.isAbilityUsed(
                        controller.getId(), 
                        new MageObjectReference(sourcePermanent, game)
                    );
        }
}

class TheFourthDoctorTriggeredAbility extends TriggeredAbilityImpl {

    TheFourthDoctorTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new FoodToken()));
        this.setRuleVisible(false);
    }

    private TheFourthDoctorTriggeredAbility(final TheFourthDoctorTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TheFourthDoctorTriggeredAbility copy() {
        return new TheFourthDoctorTriggeredAbility(this); 
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST
            || event.getType() == GameEvent.EventType.LAND_PLAYED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        // returns true if a card was played via this card's ability 
        // (i.e. same identifier and same approving object)
        if (event.hasApprovingIdentifier(MageIdentifier.TheFourthDoctorWatcher)) {
            return event
                    .getApprovingObject()
                    .getApprovingAbility()
                    .getSourceId()
                    .equals(this.getSourceId());
        }
        return false;
    }
}

// adapted from OnceEachTurnCastWatcher
class TheFourthDoctorWatcher extends Watcher {

    // we store a map of playerIds linked to a set of used approving objects.
    private final Map<UUID, Set<MageObjectReference>> usedFrom = new HashMap<>();

    TheFourthDoctorWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if ((event.getType() == GameEvent.EventType.SPELL_CAST
                ||event.getType() == GameEvent.EventType.LAND_PLAYED)
                && event.getPlayerId()!= null
                && event.hasApprovingIdentifier(MageIdentifier.TheFourthDoctorWatcher)) {
            usedFrom.computeIfAbsent(event.getPlayerId(), k -> new HashSet<>())
                    .add(event.getApprovingObject().getApprovingMageObjectReference());

        }
    }

    @Override
    public void reset() {
        super.reset();
        usedFrom.clear();
    }

    boolean isAbilityUsed(UUID playerId, MageObjectReference mor) {
        return usedFrom.getOrDefault(playerId, Collections.emptySet()).contains(mor);
    }
}

enum TheFourthDoctorCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        TheFourthDoctorWatcher watcher = game.getState().getWatcher(TheFourthDoctorWatcher.class);
        return watcher != null
                && !watcher.isAbilityUsed(source.getControllerId(), new MageObjectReference(source.getSourceId(), game));
    }
}
