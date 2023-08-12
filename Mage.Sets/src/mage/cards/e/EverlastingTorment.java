package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continuous.CantGainLifeAllEffect;
import mage.abilities.effects.common.continuous.DamageCantBePreventedEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class EverlastingTorment extends CardImpl {

    public EverlastingTorment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B/R}");

        // Players can't gain life.
        this.addAbility(new SimpleStaticAbility(new CantGainLifeAllEffect()));

        // Damage can't be prevented.
        this.addAbility(new SimpleStaticAbility(new DamageCantBePreventedEffect(
                Duration.WhileOnBattlefield, "Damage can't be prevented"
        )));

        // All damage is dealt as though its source had wither.
        this.addAbility(new SimpleStaticAbility(new DamageDealtAsIfSourceHadWitherEffect()));
    }

    private EverlastingTorment(final EverlastingTorment card) {
        super(card);
    }

    @Override
    public EverlastingTorment copy() {
        return new EverlastingTorment(this);
    }
}

class DamageDealtAsIfSourceHadWitherEffect extends ReplacementEffectImpl {

    DamageDealtAsIfSourceHadWitherEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        staticText = "All damage is dealt as though its source had wither";
    }

    private DamageDealtAsIfSourceHadWitherEffect(final DamageDealtAsIfSourceHadWitherEffect effect) {
        super(effect);
    }

    @Override
    public DamageDealtAsIfSourceHadWitherEffect copy() {
        return new DamageDealtAsIfSourceHadWitherEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        ((DamageEvent) event).setAsThoughWither(true);
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PERMANENT;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return true;
    }
}
