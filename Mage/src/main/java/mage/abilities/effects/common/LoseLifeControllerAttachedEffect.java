package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;


public class LoseLifeControllerAttachedEffect extends OneShotEffect {

    protected DynamicValue amount;

    public LoseLifeControllerAttachedEffect(int amount) {
        this(StaticValue.get(amount));
    }

    public LoseLifeControllerAttachedEffect(DynamicValue amount) {
        super(Outcome.LoseLife);
        this.amount = amount;
        setText();
    }

    protected LoseLifeControllerAttachedEffect(final LoseLifeControllerAttachedEffect effect) {
        super(effect);
        this.amount = effect.amount.copy();
    }

    @Override
    public LoseLifeControllerAttachedEffect copy() {
        return new LoseLifeControllerAttachedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent attachment = source.getSourcePermanentOrLKI(game);
        if (attachment == null || attachment.getAttachedTo() == null) {
            return false;
        }
        Permanent attachedTo = (Permanent) game.getLastKnownInformation(attachment.getAttachedTo(),
                    Zone.BATTLEFIELD, attachment.getAttachedToZoneChangeCounter());
        if (attachedTo == null) {
            return false;
        }
        Player player = game.getPlayer(attachedTo.getControllerId());
        if (player == null) {
            return false;
        }
        player.loseLife(amount.calculate(game, source, this), game, source, false);
        return true;
    }

    private void setText() {
        StringBuilder sb = new StringBuilder();
        sb.append("its controller loses ").append(amount.toString()).append(" life");
        String message = amount.getMessage();
        if (!message.isEmpty()) {
            sb.append(" for each ");
            sb.append(message);
        }
        staticText = sb.toString();
    }
}
