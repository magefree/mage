package mage.cards.l;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.ConditionalColoredManaAbility;
import mage.abilities.mana.conditional.ConditionalSpellManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LilypadVillage extends CardImpl {

    public LilypadVillage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {T} Add {U}. Spend this mana only to cast a creature spell.
        this.addAbility(new ConditionalColoredManaAbility(
                Mana.BlueMana(1), new ConditionalSpellManaBuilder(StaticFilters.FILTER_SPELL_A_CREATURE)
        ));

        // {U}, {T}: Surveil 2. Activate only if a Bird, Frog, Otter, or Rat entered the battlefield under your control this turn.
        Ability ability = new ActivateIfConditionActivatedAbility(
                new SurveilEffect(2), new ManaCostsImpl<>("{U}"), LilypadVillageCondition.instance
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability.addHint(LilypadVillageCondition.getHint()), new LilypadVillageWatcher());
    }

    private LilypadVillage(final LilypadVillage card) {
        super(card);
    }

    @Override
    public LilypadVillage copy() {
        return new LilypadVillage(this);
    }
}

enum LilypadVillageCondition implements Condition {
    instance;
    private static final Hint hint = new ConditionHint(
            instance, "A Bird, Frog, Otter, or Rat entered the battlefield under your control this turn"
    );

    public static Hint getHint() {
        return hint;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return LilypadVillageWatcher.checkPlayer(source.getControllerId(), game);
    }

    @Override
    public String toString() {
        return "a Bird, Frog, Otter, or Rat entered the battlefield under your control this turn";
    }
}

class LilypadVillageWatcher extends Watcher {

    private final Set<UUID> players = new HashSet<>();

    LilypadVillageWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ENTERS_THE_BATTLEFIELD) {
            return;
        }
        Permanent permanent = ((EntersTheBattlefieldEvent) event).getTarget();
        if (permanent != null
                && (permanent.hasSubtype(SubType.BIRD, game)
                || permanent.hasSubtype(SubType.FROG, game)
                || permanent.hasSubtype(SubType.OTTER, game)
                || permanent.hasSubtype(SubType.RAT, game))) {
            players.add(permanent.getControllerId());
        }
    }

    @Override
    public void reset() {
        super.reset();
        players.clear();
    }

    static boolean checkPlayer(UUID playerId, Game game) {
        return game
                .getState()
                .getWatcher(LilypadVillageWatcher.class)
                .players
                .contains(playerId);
    }
}
