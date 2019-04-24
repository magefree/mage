
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.ControllerLifeCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.EchoAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class VolcanoHellion extends CardImpl {

    public VolcanoHellion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");

        this.subtype.add(SubType.HELLION);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Volcano Hellion has echo {X}, where X is your life total.
        this.addAbility(new EchoAbility(new ControllerLifeCount(), "{this} has echo {X}, where X is your life total."));

        // When Volcano Hellion enters the battlefield, it deals an amount of damage of your choice to you and target creature. The damage can't be prevented.
        Ability ability = new EntersBattlefieldTriggeredAbility(new VolcanoHellionEffect(), false);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

    }

    public VolcanoHellion(final VolcanoHellion card) {
        super(card);
    }

    @Override
    public VolcanoHellion copy() {
        return new VolcanoHellion(this);
    }
}

class VolcanoHellionEffect extends OneShotEffect {

    public VolcanoHellionEffect() {
        super(Outcome.AIDontUseIt);
        this.staticText = "it deals an amount of damage of your choice to you and target creature. The damage can't be prevented";
    }

    public VolcanoHellionEffect(final VolcanoHellionEffect effect) {
        super(effect);
    }

    @Override
    public VolcanoHellionEffect copy() {
        return new VolcanoHellionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (controller != null) {
            int amount = controller.getAmount(0, Integer.MAX_VALUE, "Choose the amount of damage to deliver to you and a target creature.  The damage can't be prevented.", game);
            if (amount > 0) {
                controller.damage(amount, source.getSourceId(), game, false, false);
                if (permanent != null) {
                    permanent.damage(amount, source.getSourceId(), game, false, false);
                }
                return true;
            }
        }
        return false;
    }
}
