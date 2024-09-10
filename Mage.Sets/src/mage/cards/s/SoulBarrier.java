package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.ManaUtil;

import java.util.UUID;

/**
 * @author Galatolol
 */
public final class SoulBarrier extends CardImpl {

    public SoulBarrier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        // Whenever an opponent casts a creature spell, Soul Barrier deals 2 damage to that player unless they pay {2}.
        this.addAbility(new SpellCastOpponentTriggeredAbility(Zone.BATTLEFIELD, new SoulBarrierEffect(),
                StaticFilters.FILTER_SPELL_A_CREATURE, false, SetTargetPointer.PLAYER));
    }

    private SoulBarrier(final SoulBarrier card) {
        super(card);
    }

    @Override
    public SoulBarrier copy() {
        return new SoulBarrier(this);
    }
}

class SoulBarrierEffect extends OneShotEffect {

    SoulBarrierEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals 2 damage to that player unless they pay {2}";
    }

    private SoulBarrierEffect(final SoulBarrierEffect effect) {
        super(effect);
    }

    @Override
    public SoulBarrierEffect copy() {
        return new SoulBarrierEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        Permanent permanent = game.getPermanent(source.getSourceId());

        if (player != null && permanent != null) {
            Cost cost = ManaUtil.createManaCost(2, false);
            if (!(player.chooseUse(Outcome.Benefit, "Pay {2}?", source, game)
                    && cost.pay(source, game, source, player.getId(), false, null))) {
                player.damage(2, source.getSourceId(), source, game);
            }
            return true;
        }
        return false;
    }
}
