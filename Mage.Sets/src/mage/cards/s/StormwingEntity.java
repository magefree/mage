package mage.cards.s;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.watchers.Watcher;

/**
 * @author TheElk801
 */
public final class StormwingEntity extends CardImpl {

    private static final ConditionHint hint = new ConditionHint(
            StormwingEntityCondition.instance, "You cast an instant or sorcery spell this turn"
    );

    public StormwingEntity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // This spell costs {2}{U} less to cast if you've cast an instant or sorcery spell this turn.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SpellCostReductionSourceEffect(
                new ManaCostsImpl("{2}{U}"), StormwingEntityCondition.instance
        )).setRuleAtTheTop(true).addHint(hint), new StormwingEntityWatcher());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Prowess
        this.addAbility(new ProwessAbility());

        // When Stormwing Entity enters the battlefield, scry 2.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ScryEffect(2)));
    }

    private StormwingEntity(final StormwingEntity card) {
        super(card);
    }

    @Override
    public StormwingEntity copy() {
        return new StormwingEntity(this);
    }
}

enum StormwingEntityCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        StormwingEntityWatcher watcher = game.getState().getWatcher(StormwingEntityWatcher.class);
        return watcher != null && watcher.checkPlayer(source.getControllerId());
    }
}

class StormwingEntityWatcher extends Watcher {

    private final Set<UUID> playerMap = new HashSet<>();

    StormwingEntityWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.SPELL_CAST) {
            return;
        }
        Spell spell = game.getSpell(event.getTargetId());
        if (spell == null || !spell.isInstantOrSorcery()) {
            return;
        }
        playerMap.add(event.getPlayerId());
    }

    @Override
    public void reset() {
        playerMap.clear();
        super.reset();
    }

    boolean checkPlayer(UUID playerId) {
        return playerMap.contains(playerId);
    }
}
