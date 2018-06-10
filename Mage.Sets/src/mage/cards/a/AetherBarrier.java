
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.SacrificeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class AetherBarrier extends CardImpl {

    public AetherBarrier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        // Whenever a player casts a creature spell, that player sacrifices a permanent unless he or she pays {1}.
        this.addAbility(new SpellCastAllTriggeredAbility(Zone.BATTLEFIELD, new AetherBarrierEffect(),
                StaticFilters.FILTER_SPELL_A_CREATURE, false, SetTargetPointer.PLAYER));
    }

    public AetherBarrier(final AetherBarrier card) {
        super(card);
    }

    @Override
    public AetherBarrier copy() {
        return new AetherBarrier(this);
    }
}

class AetherBarrierEffect extends SacrificeEffect {

    AetherBarrierEffect() {
        super(new FilterPermanent("permanent to sacrifice"), 1, "that player");
        this.staticText = "that player sacrifices a permanent unless he or she pays {1}";
    }

    AetherBarrierEffect(final AetherBarrierEffect effect) {
        super(effect);
    }

    @Override
    public AetherBarrierEffect copy() {
        return new AetherBarrierEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (player != null) {
            GenericManaCost cost = new GenericManaCost(1);
            if (!cost.pay(source, game, player.getId(), player.getId(), false)) {
                super.apply(game, source);
            }
            return true;
        }
        return false;
    }
}
