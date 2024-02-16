package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author Cguy7777
 */
public final class Hipparion extends CardImpl {

    public Hipparion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HORSE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Hipparion can't block creatures with power 3 or greater unless you pay {1}.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new HipparionEffect()));
    }

    private Hipparion(final Hipparion card) {
        super(card);
    }

    @Override
    public Hipparion copy() {
        return new Hipparion(this);
    }
}

class HipparionEffect extends ReplacementEffectImpl {

    HipparionEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "{this} can't block creatures with power 3 or greater unless you pay {1}";
    }

    private HipparionEffect(final HipparionEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(event.getPlayerId());
        if (player == null) {
            return false;
        }

        Permanent hipparion = game.getPermanent(event.getSourceId());
        if (hipparion == null) {
            return false;
        }

        Permanent attacker = game.getPermanent(event.getTargetId());
        if (attacker == null || attacker.getPower().getValue() < 3) {
            return false;
        }

        ManaCost cost = new GenericManaCost(1);
        if (cost.canPay(source, source, player.getId(), game)
                && player.chooseUse(Outcome.Benefit, "Pay {1} to block creature with power 3 or greater?", source, game)) {
            return !cost.pay(source, game, source, player.getId(), false, cost);
        }

        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARE_BLOCKER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getTargetId() != null && event.getSourceId().equals(source.getSourceId());
    }

    @Override
    public HipparionEffect copy() {
        return new HipparionEffect(this);
    }
}
