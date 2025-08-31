package mage.cards.l;

import mage.MageInt;
import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.ForetellAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LifestreamsBlessing extends CardImpl {

    public LifestreamsBlessing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{G}{G}");

        // Draw X cards, where X is the greatest power among creatures you controlled as you cast this spell. If this spell was cast from exile, you gain twice X life.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(LifestreamsBlessingValue.ONCE));
        this.getSpellAbility().addEffect(new GainLifeEffect(LifestreamsBlessingValue.TWICE)
                .setText("if this spell was cast from exile, you gain twice X life"));
        this.getSpellAbility().addWatcher(new LifestreamsBlessingWatcher());

        // Foretell {4}{G}
        this.addAbility(new ForetellAbility(this, "{4}{G}"));
    }

    private LifestreamsBlessing(final LifestreamsBlessing card) {
        super(card);
    }

    @Override
    public LifestreamsBlessing copy() {
        return new LifestreamsBlessing(this);
    }
}

enum LifestreamsBlessingValue implements DynamicValue {
    ONCE(false),
    TWICE(true);
    private final boolean flag;

    LifestreamsBlessingValue(boolean flag) {
        this.flag = flag;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        if (flag && !Optional
                .ofNullable(sourceAbility)
                .map(Ability::getSourceId)
                .map(game::getSpell)
                .map(Spell::getFromZone)
                .map(Zone.EXILED::match)
                .orElse(false)) {
            return 0;
        }
        return (flag ? 2 : 1) * LifestreamsBlessingWatcher.getValue(game, sourceAbility);
    }

    @Override
    public LifestreamsBlessingValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "the greatest power among creatures you controlled as you cast this spell";
    }

    @Override
    public String toString() {
        return "X";
    }
}

class LifestreamsBlessingWatcher extends Watcher {

    private final Map<MageObjectReference, Integer> map = new HashMap<>();

    LifestreamsBlessingWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.SPELL_CAST) {
            return;
        }
        Spell spell = game.getSpell(event.getTargetId());
        if (spell == null) {
            return;
        }
        map.put(
                new MageObjectReference(spell.getSpellAbility().getSourceId(), game),
                game.getBattlefield()
                        .getActivePermanents(
                                StaticFilters.FILTER_CONTROLLED_CREATURE, spell.getControllerId(), game
                        )
                        .stream()
                        .map(MageObject::getPower)
                        .mapToInt(MageInt::getValue)
                        .max()
                        .orElse(0)
        );
    }

    @Override
    public void reset() {
        super.reset();
        map.clear();
    }

    static int getValue(Game game, Ability source) {
        return game
                .getState()
                .getWatcher(LifestreamsBlessingWatcher.class)
                .map
                .getOrDefault(new MageObjectReference(
                        source.getSourceId(), source.getStackMomentSourceZCC(), game
                ), 0);
    }
}
