package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.PutCardIntoGraveFromAnywhereAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.events.PreventDamageEvent;
import mage.game.events.PreventedDamageEvent;

import java.util.Objects;
import java.util.UUID;

/**
 * @author Plopman
 */
public final class EnergyField extends CardImpl {

    public EnergyField(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");


        // Prevent all damage that would be dealt to you by sources you don't control.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new EnergyFieldEffect()));
        // When a card is put into your graveyard from anywhere, sacrifice Energy Field.
        this.addAbility(new PutCardIntoGraveFromAnywhereAllTriggeredAbility(
                new SacrificeSourceEffect(), false, TargetController.YOU));

    }

    private EnergyField(final EnergyField card) {
        super(card);
    }

    @Override
    public EnergyField copy() {
        return new EnergyField(this);
    }
}

class EnergyFieldEffect extends PreventionEffectImpl {

    public EnergyFieldEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "Prevent all damage that would be dealt to you by sources you don't control";
    }

    private EnergyFieldEffect(final EnergyFieldEffect effect) {
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
            if (event.getTargetId().equals(source.getControllerId()) && !Objects.equals(game.getControllerId(event.getSourceId()), source.getControllerId())) {
                return super.applies(event, source, game);
            }
        }
        return false;
    }

    @Override
    public EnergyFieldEffect copy() {
        return new EnergyFieldEffect(this);
    }
}
