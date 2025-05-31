package mage.cards.s;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.PreventionEffectData;
import mage.abilities.effects.common.PreventNextDamageFromChosenSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class Shadowbane extends CardImpl {

    public Shadowbane(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // The next time a source of your choice would deal damage to you and/or creatures you control this turn, prevent that damage. If damage from a black source is prevented this way, you gain that much life.
        this.getSpellAbility().addEffect(new ShadowbanePreventionEffect());
    }

    private Shadowbane(final Shadowbane card) {
        super(card);
    }

    @Override
    public Shadowbane copy() {
        return new Shadowbane(this);
    }
}

class ShadowbanePreventionEffect extends PreventNextDamageFromChosenSourceEffect {

    ShadowbanePreventionEffect() {
        super(Duration.EndOfTurn, false, ShadowbanePreventionApplier.instance);
    }

    private ShadowbanePreventionEffect(final ShadowbanePreventionEffect effect) {
        super(effect);
    }

    @Override
    public PreventNextDamageFromChosenSourceEffect copy() {
        return new ShadowbanePreventionEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!super.applies(event, source, game)) {
            return false;
        }
        UUID controllerId = source.getControllerId();
        UUID targetId = event.getTargetId();
        if (targetId.equals(controllerId)) {
            return true; // damage to you
        }
        Permanent permanent = game.getPermanent(targetId);
        return StaticFilters.FILTER_CONTROLLED_CREATURE.match(permanent, controllerId, source, game); // damage to creatures you control
    }

}

enum ShadowbanePreventionApplier implements PreventNextDamageFromChosenSourceEffect.ApplierOnPrevention {
    instance;

    public String getText() {
        return "If damage from a black source is prevented this way, you gain that much life";
    }

    public boolean apply(PreventionEffectData data, Target target, GameEvent event, Ability source, Game game) {
        if (data == null || data.getPreventedDamage() <= 0) {
            return false;
        }
        MageObject sourceObject = game.getObject(target.getFirstTarget());
        if (!sourceObject.getColor(game).isBlack()) {
            return false;
        }
        int prevented = data.getPreventedDamage();
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        controller.gainLife(prevented, game, source);
        return true;
    }
}