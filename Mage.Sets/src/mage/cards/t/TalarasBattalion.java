package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CastOnlyIfConditionIsTrueAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.stack.Spell;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TalarasBattalion extends CardImpl {

    public TalarasBattalion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Cast Talara's Battalion only if you've cast another green spell this turn.
        this.addAbility(new CastOnlyIfConditionIsTrueAbility(
                TalarasBattalionWatcher::checkSpell,
                "cast this spell only if you've cast another green spell this turn"
        ), new TalarasBattalionWatcher());

    }

    private TalarasBattalion(final TalarasBattalion card) {
        super(card);
    }

    @Override
    public TalarasBattalion copy() {
        return new TalarasBattalion(this);
    }
}

class TalarasBattalionWatcher extends Watcher {

    private final Set<UUID> playerSet = new HashSet<>();

    TalarasBattalionWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != EventType.SPELL_CAST) {
            return;
        }
        Spell spell = game.getSpell(event.getSourceId());
        if (spell != null && spell.getColor(game).isGreen()) {
            playerSet.add(spell.getControllerId());
        }
    }

    @Override
    public void reset() {
        super.reset();
        playerSet.clear();
    }

    static boolean checkSpell(Game game, Ability source) {
        return game.getState().getWatcher(TalarasBattalionWatcher.class).playerSet.contains(source.getControllerId());
    }
}
