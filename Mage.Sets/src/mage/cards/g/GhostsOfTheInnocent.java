
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.PreventionEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author LevelX2
 */
public final class GhostsOfTheInnocent extends CardImpl {

    public GhostsOfTheInnocent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}{W}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // If a source would deal damage to a creature or player, it deals half that damage, rounded down, to that creature or player instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GhostsOfTheInnocentPreventDamageEffect()));
    }

    public GhostsOfTheInnocent(final GhostsOfTheInnocent card) {
        super(card);
    }

    @Override
    public GhostsOfTheInnocent copy() {
        return new GhostsOfTheInnocent(this);
    }
}

class GhostsOfTheInnocentPreventDamageEffect extends ReplacementEffectImpl implements PreventionEffect {

    public GhostsOfTheInnocentPreventDamageEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Damage);
        staticText = "If a source would deal damage to a permanent or player, it deals half that damage, rounded down, to that permanent or player instead";
    }

    public GhostsOfTheInnocentPreventDamageEffect(final GhostsOfTheInnocentPreventDamageEffect effect) {
        super(effect);
    }

    @Override
    public GhostsOfTheInnocentPreventDamageEffect copy() {
        return new GhostsOfTheInnocentPreventDamageEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DAMAGE_CREATURE
                || event.getType() == EventType.DAMAGE_PLAYER
                || event.getType() == EventType.DAMAGE_PLANESWALKER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        int amount = (int) Math.ceil(event.getAmount() / 2.0);
        GameEvent preventEvent = new GameEvent(GameEvent.EventType.PREVENT_DAMAGE, event.getTargetId(), source.getSourceId(), source.getControllerId(), amount, false);
        if (!game.replaceEvent(preventEvent)) {
            event.setAmount(event.getAmount() - amount);
            game.fireEvent(GameEvent.getEvent(GameEvent.EventType.PREVENTED_DAMAGE, event.getTargetId(), source.getSourceId(), source.getControllerId(), amount));
        }
        return false;
    }
}
