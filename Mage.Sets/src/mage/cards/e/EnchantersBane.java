package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetEnchantmentPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EnchantersBane extends CardImpl {

    public EnchantersBane(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");

        // At the beginning of your end step, target enchantment deals damage equal to its converted mana cost to its controller unless that player sacrifices it.
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                new EnchantersBaneEffect(), TargetController.YOU, false
        );
        ability.addTarget(new TargetEnchantmentPermanent());
        this.addAbility(ability);
    }

    private EnchantersBane(final EnchantersBane card) {
        super(card);
    }

    @Override
    public EnchantersBane copy() {
        return new EnchantersBane(this);
    }
}

class EnchantersBaneEffect extends OneShotEffect {

    public EnchantersBaneEffect() {
        super(Outcome.Benefit);
        this.staticText = "target enchantment deals damage equal to "
                + "its mana value to its controller "
                + "unless that player sacrifices it";
    }

    private EnchantersBaneEffect(final EnchantersBaneEffect effect) {
        super(effect);
    }

    @Override
    public EnchantersBaneEffect copy() {
        return new EnchantersBaneEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        Player player = game.getPlayer(permanent.getControllerId());
        if (player == null) {
            return false;
        }
        if (player.chooseUse(Outcome.GainLife, "Sacrifice " + permanent.getLogName() + "?", source, game)) {
            permanent.sacrifice(source, game);
        } else {
            player.damage(permanent.getManaValue(), permanent.getId(), source, game);
        }
        return true;
    }
}
