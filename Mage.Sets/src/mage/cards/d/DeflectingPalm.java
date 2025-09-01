package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.effects.PreventionEffectData;
import mage.abilities.effects.common.PreventNextDamageFromChosenSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.Target;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class DeflectingPalm extends CardImpl {

    public DeflectingPalm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}{W}");

        // The next time a source of your choice would deal damage to you this turn, prevent that damage. If damage is prevented this way, Deflecting Palm deals that much damage to that source's controller.
        this.getSpellAbility().addEffect(
                new PreventNextDamageFromChosenSourceEffect(
                        Duration.EndOfTurn, true,
                        DeflectingPalmPreventionApplier.instance
                )
        );
    }

    private DeflectingPalm(final DeflectingPalm card) {
        super(card);
    }

    @Override
    public DeflectingPalm copy() {
        return new DeflectingPalm(this);
    }
}

enum DeflectingPalmPreventionApplier implements PreventNextDamageFromChosenSourceEffect.ApplierOnPrevention {
    instance;

    public boolean apply(PreventionEffectData data, Target target, GameEvent event, Ability source, Game game) {
        if (data == null || data.getPreventedDamage() <= 0) {
            return false;
        }
        int prevented = data.getPreventedDamage();
        UUID objectControllerId = game.getControllerId(target.getFirstTarget());
        Player objectController = game.getPlayer(objectControllerId);
        if (objectController == null) {
            return false;
        }
        objectController.damage(prevented, source.getSourceId(), source, game);
        return true;
    }

    public String getText() {
        return "If damage is prevented this way, {this} deals that much damage to that source's controller";
    }
}