package mage.cards.a;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.watchers.Watcher;

/**
 *
 * @author TheElk801
 */
public final class ArclightPhoenix extends CardImpl {

    public ArclightPhoenix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.PHOENIX);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // At the beginning of combat on your turn, if you cast 3 or more instants and/or sorceries this turn, you may return Arclight Phoenix from your graveyard to the battlefield.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfCombatTriggeredAbility(
                        Zone.GRAVEYARD,
                        new ReturnSourceFromGraveyardToBattlefieldEffect(),
                        TargetController.YOU, true, false
                ), ArclightPhoenixCondition.instance,
                "At the beginning of combat on your turn, "
                + "if you've cast three or more instant "
                + "and sorcery spells this turn, you may return {this} "
                + "from your graveyard to the battlefield."
        ), new ArclightPhoenixWatcher());
    }

    public ArclightPhoenix(final ArclightPhoenix card) {
        super(card);
    }

    @Override
    public ArclightPhoenix copy() {
        return new ArclightPhoenix(this);
    }
}

enum ArclightPhoenixCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        ArclightPhoenixWatcher watcher
                = (ArclightPhoenixWatcher) game.getState().getWatchers().get(
                        ArclightPhoenixWatcher.class.getSimpleName()
                );
        return watcher != null && watcher.getInstantSorceryCount(source.getControllerId()) > 2;
    }
}

class ArclightPhoenixWatcher extends Watcher {

    private final Map<UUID, Integer> instantSorceryCount = new HashMap();

    public ArclightPhoenixWatcher() {
        super(ArclightPhoenixWatcher.class.getSimpleName(), WatcherScope.GAME);
    }

    public ArclightPhoenixWatcher(final ArclightPhoenixWatcher watcher) {
        super(watcher);
        this.instantSorceryCount.putAll(watcher.instantSorceryCount);
    }

    @Override
    public ArclightPhoenixWatcher copy() {
        return new ArclightPhoenixWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell == null || !spell.isInstantOrSorcery()) {
                return;
            }
            this.instantSorceryCount.putIfAbsent(spell.getControllerId(), 0);
            this.instantSorceryCount.compute(
                    spell.getControllerId(), (k, a) -> a + 1
            );
        }
    }

    @Override
    public void reset() {
        super.reset();
        this.instantSorceryCount.clear();
    }

    public int getInstantSorceryCount(UUID playerId) {
        return this.instantSorceryCount.getOrDefault(playerId, 0);
    }
}
