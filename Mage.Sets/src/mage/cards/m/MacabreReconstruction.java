package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.target.common.TargetCardInYourGraveyard;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MacabreReconstruction extends CardImpl {

    public MacabreReconstruction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}");

        // This spell costs {2} less to cast if a creature card was put into your graveyard from anywhere this turn.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(2, MacabreReconstructionCondition.instance)
        ).setRuleAtTheTop(true).addHint(MacabreReconstructionCondition.getHint()), new MacabreReconstructionWatcher());

        // Return up to two target creature cards from your graveyard to your hand.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(
                0, 2, StaticFilters.FILTER_CARD_CREATURES
        ));
    }

    private MacabreReconstruction(final MacabreReconstruction card) {
        super(card);
    }

    @Override
    public MacabreReconstruction copy() {
        return new MacabreReconstruction(this);
    }
}

enum MacabreReconstructionCondition implements Condition {
    instance;
    private static final Hint hint = new ConditionHint(
            instance, "A card was put into your graveyard this turn"
    );

    public static Hint getHint() {
        return hint;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return MacabreReconstructionWatcher.checkPlayer(source.getControllerId(), game);
    }

    @Override
    public String toString() {
        return "a creature card was put into your graveyard from anywhere this turn";
    }
}

class MacabreReconstructionWatcher extends Watcher {

    private final Set<UUID> set = new HashSet<>();

    MacabreReconstructionWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ZONE_CHANGE) {
            return;
        }
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (Zone.GRAVEYARD.match(zEvent.getToZone())) {
            set.add(game.getOwnerId(zEvent.getTargetId()));
        }
    }

    @Override
    public void reset() {
        super.reset();
        set.clear();
    }

    static boolean checkPlayer(UUID playerId, Game game) {
        return game
                .getState()
                .getWatcher(MacabreReconstructionWatcher.class)
                .set
                .contains(playerId);
    }
}
