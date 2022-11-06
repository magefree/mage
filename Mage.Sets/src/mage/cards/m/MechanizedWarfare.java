package mage.cards.m;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class MechanizedWarfare extends CardImpl {

    public MechanizedWarfare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}{R}");

        // If a red or artifact source you control would deal damage to an opponent or a
        // permanent an opponent controls, it deals that much damage plus 1 instead.
        this.addAbility(new SimpleStaticAbility(new MechanizedWarfareEffect()));
    }

    private MechanizedWarfare(final MechanizedWarfare card) {
        super(card);
    }

    @Override
    public MechanizedWarfare copy() {
        return new MechanizedWarfare(this);
    }
}

class MechanizedWarfareEffect extends ReplacementEffectImpl {

    MechanizedWarfareEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Damage);
        this.staticText = "If a red or artifact source you control would deal damage to an opponent "
                + "or a permanent an opponent controls, it deals that much damage plus 1 instead.";
    }

    private MechanizedWarfareEffect(final MechanizedWarfareEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowInc(event.getAmount(), 1));
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        switch (event.getType()) {
            case DAMAGE_PERMANENT:
            case DAMAGE_PLAYER:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null
                || !controller.hasOpponent(getControllerOrSelf(event.getTargetId(), game), game)
                || !source.isControlledBy(game.getControllerId(event.getSourceId()))) {
            return false;
        }
        MageObject sourceObject;
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
        if (sourcePermanent == null) {
            sourceObject = game.getObject(event.getSourceId());
        } else {
            sourceObject = sourcePermanent;
        }

        return sourceObject != null
                && event.getAmount() > 0
                && (sourceObject.getColor(game).isRed() || sourceObject.isArtifact());
    }

    private static UUID getControllerOrSelf(UUID id, Game game) {
        UUID outId = game.getControllerId(id);
        return outId == null ? id : outId;
    }

    @Override
    public MechanizedWarfareEffect copy() {
        return new MechanizedWarfareEffect(this);
    }
}