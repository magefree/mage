
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.constants.SubType;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagePlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author TheElk801
 */
public final class WeatheredBodyguards extends CardImpl {

    public WeatheredBodyguards(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // As long as Weathered Bodyguards is untapped, all combat damage that would be dealt to you by unblocked creatures is dealt to Weathered Bodyguards instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new WeatheredBodyguardsEffect()));

        // Morph {3}{W}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{3}{W}")));

    }

    private WeatheredBodyguards(final WeatheredBodyguards card) {
        super(card);
    }

    @Override
    public WeatheredBodyguards copy() {
        return new WeatheredBodyguards(this);
    }
}

class WeatheredBodyguardsEffect extends ReplacementEffectImpl {

    WeatheredBodyguardsEffect() {
        super(Duration.WhileOnBattlefield, Outcome.RedirectDamage);
        staticText = "As long as {this} is untapped, all combat damage that would be dealt to you by unblocked creatures is dealt to {this} instead";
    }

    WeatheredBodyguardsEffect(final WeatheredBodyguardsEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        DamagePlayerEvent damageEvent = (DamagePlayerEvent) event;
        Permanent attacker = game.getPermanentOrLKIBattlefield(damageEvent.getSourceId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null && !permanent.isTapped() && damageEvent.isCombatDamage() && attacker != null && attacker.isAttacking() && !attacker.isBlocked(game)) {
            permanent.damage(damageEvent.getAmount(), event.getSourceId(), source, game, damageEvent.isCombatDamage(), damageEvent.isPreventable());
            return true;
        }
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getPlayerId().equals(source.getControllerId());
    }

    @Override
    public WeatheredBodyguardsEffect copy() {
        return new WeatheredBodyguardsEffect(this);
    }
}
