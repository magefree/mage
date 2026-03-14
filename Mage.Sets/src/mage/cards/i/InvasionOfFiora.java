package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SiegeAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.counter.RemoveAllCountersPermanentTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.WatcherScope;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.target.TargetPermanent;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvasionOfFiora extends TransformingDoubleFacedCard {

    private static final FilterPermanent filterLegend = new FilterCreaturePermanent("legendary creatures");
    private static final FilterPermanent filterNonLegend = new FilterCreaturePermanent("nonlegendary creatures");

    static {
        filterLegend.add(SuperType.LEGENDARY.getPredicate());
        filterNonLegend.add(Predicates.not(SuperType.LEGENDARY.getPredicate()));
    }

    public InvasionOfFiora(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{}, new CardType[]{CardType.BATTLE}, new SubType[]{SubType.SIEGE}, "{4}{B}{B}",
                "Marchesa, Resolute Monarch",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.NOBLE}, "B"
        );

        // Invasion of Fiora
        this.getLeftHalfCard().setStartingDefense(4);

        // (As a Siege enters, choose an opponent to protect it. You and others can attack it. When it's defeated, exile it, then cast it transformed.)
        this.getLeftHalfCard().addAbility(new SiegeAbility());

        // When Invasion of Fiora enters the battlefield, choose one or both --

        // * Destroy all legendary creatures.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyAllEffect(filterLegend));
        ability.getModes().setMinModes(1);
        ability.getModes().setMaxModes(2);

        // * Destroy all nonlegendary creatures.
        ability.addMode(new Mode(new DestroyAllEffect(filterNonLegend)));
        ability.addWatcher(new MarchesaResoluteMonarchWatcher());
        this.getLeftHalfCard().addAbility(ability);

        // Marchesa, Resolute Monarch
        this.getRightHalfCard().setPT(3, 6);

        // Menace
        this.getRightHalfCard().addAbility(new MenaceAbility(false));

        // Deathtouch
        this.getRightHalfCard().addAbility(DeathtouchAbility.getInstance());

        // Whenever Marchesa, Resolute Monarch attacks, remove all counters from up to one target permanent.
        Ability attackAbility = new AttacksTriggeredAbility(new RemoveAllCountersPermanentTargetEffect());
        attackAbility.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_PERMANENT));
        this.getRightHalfCard().addAbility(attackAbility);

        // At the beginning of your upkeep, if you haven't been dealt combat damage since your last turn, you draw a card and you lose 1 life.
        Ability upkeepAbility = new BeginningOfUpkeepTriggeredAbility(new DrawCardSourceControllerEffect(1, true))
                .withInterveningIf(MarchesaResoluteMonarchCondition.instance);
        upkeepAbility.addEffect(new LoseLifeSourceControllerEffect(1).concatBy("and"));
        this.getRightHalfCard().addAbility(upkeepAbility);
    }

    private InvasionOfFiora(final InvasionOfFiora card) {
        super(card);
    }

    @Override
    public InvasionOfFiora copy() {
        return new InvasionOfFiora(this);
    }
}

enum MarchesaResoluteMonarchCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return MarchesaResoluteMonarchWatcher.checkPlayer(game, source);
    }

    @Override
    public String toString() {
        return "you haven't been dealt combat damage since your last turn";
    }
}

class MarchesaResoluteMonarchWatcher extends Watcher {

    private final Set<UUID> players = new HashSet<>();

    MarchesaResoluteMonarchWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        switch (event.getType()) {
            case DAMAGED_PLAYER:
                if (((DamagedEvent) event).isCombatDamage()) {
                    players.add(event.getTargetId());
                }
                return;
            case END_TURN_STEP_POST:
                players.remove(game.getActivePlayerId());
                return;

        }
    }

    static boolean checkPlayer(Game game, Ability source) {
        return !game
                .getState()
                .getWatcher(MarchesaResoluteMonarchWatcher.class)
                .players
                .contains(source.getControllerId());
    }
}
