
package mage.cards.c;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.PreventionEffectData;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author emerald000
 */
public final class ChannelHarm extends CardImpl {

    public ChannelHarm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{5}{W}");

        // Prevent all damage that would be dealt to you and permanents you control this turn by sources you don't control. If damage is prevented this way, you may have Channel Harm deal that much damage to target creature.
        this.getSpellAbility().addEffect(new ChannelHarmEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private ChannelHarm(final ChannelHarm card) {
        super(card);
    }

    @Override
    public ChannelHarm copy() {
        return new ChannelHarm(this);
    }
}

class ChannelHarmEffect extends PreventionEffectImpl {

    ChannelHarmEffect() {
        super(Duration.EndOfTurn, Integer.MAX_VALUE, false, false);
        staticText = "Prevent all damage that would be dealt to you and permanents you control this turn by sources you don't control. If damage is prevented this way, you may have {this} deal that much damage to target creature";
    }

    private ChannelHarmEffect(final ChannelHarmEffect effect) {
        super(effect);
    }

    @Override
    public ChannelHarmEffect copy() {
        return new ChannelHarmEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player sourceController = game.getPlayer(source.getControllerId());
        PreventionEffectData preventionData = preventDamageAction(event, source, game);
        if (preventionData.getPreventedDamage() > 0) {
            Permanent targetCreature = game.getPermanent(source.getFirstTarget());
            if (targetCreature != null) {
                if (sourceController != null && sourceController.chooseUse(outcome, "Have " + preventionData.getPreventedDamage() + " damage dealt to " + targetCreature.getLogName() + "?", source, game)) {
                    targetCreature.damage(preventionData.getPreventedDamage(), source.getSourceId(), source, game, false, true);
                }
            }
        }
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)) {
            Permanent targetPermanent = game.getPermanent(event.getTargetId());
            if ((targetPermanent != null && targetPermanent.isControlledBy(source.getControllerId()))
                    || event.getTargetId().equals(source.getControllerId())) {
                MageObject damageSource = game.getObject(event.getSourceId());
                if (damageSource instanceof Controllable) {
                    return !((Controllable) damageSource).isControlledBy(source.getControllerId());
                }
                else if (damageSource instanceof Card) {
                    return !((Card) damageSource).isOwnedBy(source.getControllerId());
                }
            }
        }
        return false;
    }
}
