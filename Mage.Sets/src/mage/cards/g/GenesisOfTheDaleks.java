package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.FaceVillainousChoice;
import mage.choices.VillainousChoice;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.token.DalekToken;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.watchers.Watcher;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GenesisOfTheDaleks extends CardImpl {

    public GenesisOfTheDaleks(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{B}{B}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after IV.)
        SagaAbility sagaAbility = new SagaAbility(this, SagaChapter.CHAPTER_IV);

        // I, II, III -- Create a 3/3 black Dalek artifact creature token with menace for each lore counter on Genesis of the Daleks.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_III,
                new CreateTokenEffect(new DalekToken(), new CountersSourceCount(CounterType.LORE))
        );

        // IV -- Target opponent faces a villainous choice -- Destroy all Dalek creatures and each of your opponents loses life equal to the total power of Daleks that died this turn, or destroy all non-Dalek creatures.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_IV,
                new GenesisOfTheDaleksEffect(), new TargetOpponent()
        );
        this.addAbility(sagaAbility, new GenesisOfTheDaleksWatcher());
    }

    private GenesisOfTheDaleks(final GenesisOfTheDaleks card) {
        super(card);
    }

    @Override
    public GenesisOfTheDaleks copy() {
        return new GenesisOfTheDaleks(this);
    }
}

class GenesisOfTheDaleksEffect extends OneShotEffect {

    private static final FaceVillainousChoice choice = new FaceVillainousChoice(
            Outcome.LoseLife, new GenesisOfTheDaleksFirstChoice(), new GenesisOfTheDaleksSecondChoice()
    );

    GenesisOfTheDaleksEffect() {
        super(Outcome.Benefit);
        staticText = "target opponent " + choice.generateRule();
    }

    private GenesisOfTheDaleksEffect(final GenesisOfTheDaleksEffect effect) {
        super(effect);
    }

    @Override
    public GenesisOfTheDaleksEffect copy() {
        return new GenesisOfTheDaleksEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return Optional.ofNullable(getTargetPointer().getFirst(game, source))
                .map(game::getPlayer)
                .map(player -> choice.faceChoice(player, game, source))
                .orElse(false);
    }
}

class GenesisOfTheDaleksFirstChoice extends VillainousChoice {

    private static final FilterPermanent filter = new FilterCreaturePermanent(SubType.DALEK, "");

    GenesisOfTheDaleksFirstChoice() {
        super("Destroy all Dalek creatures and each of your opponents loses life equal to the total power of Daleks that died this turn", "Destroy all Daleks and lose life");
    }

    @Override
    public boolean doChoice(Player player, Game game, Ability source) {
        new DestroyAllEffect(filter).apply(game, source);
        game.processAction();
        int amount = game.getState().getWatcher(GenesisOfTheDaleksWatcher.class).getPower();
        if (amount > 0) {
            for (UUID opponentId : game.getOpponents(source.getControllerId())) {
                Optional.ofNullable(opponentId)
                        .map(game::getPlayer)
                        .ifPresent(opponent -> opponent.loseLife(amount, game, source, false));
            }
        }
        return true;
    }
}

class GenesisOfTheDaleksSecondChoice extends VillainousChoice {

    private static final FilterPermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(Predicates.not(SubType.DALEK.getPredicate()));
    }

    GenesisOfTheDaleksSecondChoice() {
        super("destroy all non-Dalek creatures", "Destroy all non-Daleks");
    }

    @Override
    public boolean doChoice(Player player, Game game, Ability source) {
        return new DestroyAllEffect(filter).apply(game, source);
    }
}

class GenesisOfTheDaleksWatcher extends Watcher {

    private int power = 0;

    GenesisOfTheDaleksWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ZONE_CHANGE) {
            return;
        }
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.isDiesEvent() && zEvent.getTarget().hasSubtype(SubType.DALEK, game)) {
            power += zEvent.getTarget().getPower().getValue();
        }
    }

    @Override
    public void reset() {
        super.reset();
        power = 0;
    }

    public int getPower() {
        return power;
    }
}
