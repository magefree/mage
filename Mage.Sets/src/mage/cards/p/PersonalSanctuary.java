package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.events.PreventDamageEvent;
import mage.game.events.PreventedDamageEvent;

import java.util.UUID;

/**
 * @author nantuko
 */
public final class PersonalSanctuary extends CardImpl {

    public PersonalSanctuary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");


        // During your turn, prevent all damage that would be dealt to you.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PersonalSanctuaryEffect()));
    }

    private PersonalSanctuary(final PersonalSanctuary card) {
        super(card);
    }

    @Override
    public PersonalSanctuary copy() {
        return new PersonalSanctuary(this);
    }
}

class PersonalSanctuaryEffect extends PreventionEffectImpl {

    public PersonalSanctuaryEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "During your turn, prevent all damage that would be dealt to you";
    }

    private PersonalSanctuaryEffect(final PersonalSanctuaryEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        GameEvent preventEvent = new PreventDamageEvent(event.getTargetId(), source.getSourceId(), source, source.getControllerId(), event.getAmount(), ((DamageEvent) event).isCombatDamage());
        if (!game.replaceEvent(preventEvent)) {
            int damage = event.getAmount();
            event.setAmount(0);
            game.informPlayers("Damage has been prevented: " + damage);
            game.fireEvent(new PreventedDamageEvent(event.getTargetId(), source.getSourceId(), source, source.getControllerId(), damage));
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGE_PLAYER) {
            if (event.getTargetId().equals(source.getControllerId()) && game.isActivePlayer(source.getControllerId()))
                return super.applies(event, source, game);
        }
        return false;
    }

    @Override
    public PersonalSanctuaryEffect copy() {
        return new PersonalSanctuaryEffect(this);
    }
}
