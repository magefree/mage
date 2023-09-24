
package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.DamageEverythingEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.watchers.common.DamagedByWatcher;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class Flamebreak extends CardImpl {

    private static final FilterCreaturePermanent filter1 = new FilterCreaturePermanent("creature without flying");

    static {
        filter1.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public Flamebreak(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}{R}{R}");


        // Flamebreak deals 3 damage to each creature without flying and each player. Creatures dealt damage this way can't be regenerated this turn.
        this.getSpellAbility().addEffect(new DamageEverythingEffect(StaticValue.get(3), filter1));
        this.getSpellAbility().addEffect(new FlamebreakCantRegenerateEffect());
        this.getSpellAbility().addWatcher(new DamagedByWatcher(false));
    }

    private Flamebreak(final Flamebreak card) {
        super(card);
    }

    @Override
    public Flamebreak copy() {
        return new Flamebreak(this);
    }
}

class FlamebreakCantRegenerateEffect extends ContinuousRuleModifyingEffectImpl {

    public FlamebreakCantRegenerateEffect() {
        super(Duration.EndOfTurn, Outcome.Detriment);
        staticText = "Creatures dealt damage this way can't be regenerated this turn";
    }

    private FlamebreakCantRegenerateEffect(final FlamebreakCantRegenerateEffect effect) {
        super(effect);
    }

    @Override
    public FlamebreakCantRegenerateEffect copy() {
        return new FlamebreakCantRegenerateEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.REGENERATE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        DamagedByWatcher watcher = game.getState().getWatcher(DamagedByWatcher.class, source.getSourceId());
        return watcher != null && watcher.wasDamaged(event.getTargetId(), game);
    }

}
