package mage.abilities.effects.common.counter;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.counters.Counter;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author nantuko
 */
public class AddCountersControllerEffect extends OneShotEffect {

    private Counter counter;
    private final boolean enchantedEquipped;

    /**
     * @param counter           Counter to add. Includes type and amount.
     * @param enchantedEquipped If true, not source controller will get the
     *                          counter, but the permanent's controller that the source permanent
     *                          enchants or equippes.
     */
    public AddCountersControllerEffect(Counter counter, boolean enchantedEquipped) {
        super(Outcome.Benefit);
        this.counter = counter.copy();
        this.enchantedEquipped = enchantedEquipped;
        staticText = "its controller gets " + counter.getDescription();
    }

    public AddCountersControllerEffect(final AddCountersControllerEffect effect) {
        super(effect);
        if (effect.counter != null) {
            this.counter = effect.counter.copy();
        }
        this.enchantedEquipped = effect.enchantedEquipped;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID uuid = source.getControllerId();
        if (this.enchantedEquipped) {
            Permanent enchantment = game.getPermanent(source.getSourceId());
            if (enchantment != null && enchantment.getAttachedTo() != null) {
                UUID eUuid = enchantment.getAttachedTo();
                Permanent permanent = game.getPermanent(eUuid);
                if (permanent != null) {
                    uuid = permanent.getControllerId();
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        Player player = game.getPlayer(uuid);
        if (player != null) {
            player.addCounters(counter, source.getControllerId(), source, game);
            return true;
        }
        return false;
    }

    @Override
    public AddCountersControllerEffect copy() {
        return new AddCountersControllerEffect(this);
    }
}
