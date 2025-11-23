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
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.Objects;
import java.util.UUID;

/**
 * @author Plopman
 */
public final class EnergyField extends CardImpl {

    public EnergyField(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");


        // Prevent all damage that would be dealt to you by sources you don't control.
        this.addAbility(new SimpleStaticAbility(new EnergyFieldEffect()));
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

    EnergyFieldEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "Prevent all damage that would be dealt to you by sources you don't control";
    }

    private EnergyFieldEffect(final EnergyFieldEffect effect) {
        super(effect);
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
