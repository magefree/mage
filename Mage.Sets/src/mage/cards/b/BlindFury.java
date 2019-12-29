package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continuous.LoseAbilityAllEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.DamageCreatureEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BlindFury extends CardImpl {

    public BlindFury(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}{R}");

        // All creatures lose trample until end of turn. If a creature would deal combat damage to a creature this turn, it deals double that damage to that creature instead.
        this.getSpellAbility().addEffect(new LoseAbilityAllEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURES
        ).setText("All creatures lose trample until end of turn."));
        this.getSpellAbility().addEffect(new FurnaceOfRathEffect());
    }

    private BlindFury(final BlindFury card) {
        super(card);
    }

    @Override
    public BlindFury copy() {
        return new BlindFury(this);
    }
}

class FurnaceOfRathEffect extends ReplacementEffectImpl {

    FurnaceOfRathEffect() {
        super(Duration.EndOfTurn, Outcome.Damage);
        staticText = "If a creature would deal combat damage to a creature this turn, " +
                "it deals double that damage to that creature instead";
    }

    private FurnaceOfRathEffect(final FurnaceOfRathEffect effect) {
        super(effect);
    }

    @Override
    public FurnaceOfRathEffect copy() {
        return new FurnaceOfRathEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_CREATURE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getPermanent(event.getSourceId());
        return permanent != null
                && permanent.isCreature()
                && ((DamageCreatureEvent) event).isCombatDamage();

    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.addWithOverflowCheck(event.getAmount(), event.getAmount()));
        return false;
    }
}
