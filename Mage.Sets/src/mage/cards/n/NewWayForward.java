package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.PreventionEffectData;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.PreventNextDamageFromChosenSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.Target;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NewWayForward extends CardImpl {

    public NewWayForward(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}{R}{W}");

        // The next time a source of your choice would deal damage to you this turn, prevent that damage. When damage is prevented this way, New Way Forward deals that much damage to that source's controller and you draw that many cards.
        this.getSpellAbility().addEffect(
                new PreventNextDamageFromChosenSourceEffect(
                        Duration.EndOfTurn, true,
                        NewWayForwardPreventionApplier.instance
                )
        );
    }

    private NewWayForward(final NewWayForward card) {
        super(card);
    }

    @Override
    public NewWayForward copy() {
        return new NewWayForward(this);
    }
}

enum NewWayForwardPreventionApplier implements PreventNextDamageFromChosenSourceEffect.ApplierOnPrevention {
    instance;

    public boolean apply(PreventionEffectData data, Target target, GameEvent event, Ability source, Game game) {
        if (data == null || data.getPreventedDamage() <= 0) {
            return false;
        }
        int prevented = data.getPreventedDamage();
        UUID objectControllerId = game.getControllerId(target.getFirstTarget());
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new DamageTargetEffect(prevented)
                        .setTargetPointer(new FixedTarget(objectControllerId)),
                false
        );
        ability.addEffect(new DrawCardSourceControllerEffect(prevented));
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }

    public String getText() {
        return "When damage is prevented this way, {this} deals that much damage to that source's controller and you draw that many cards";
    }
}