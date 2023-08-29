
package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityControllerEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author BetaSteward
 */
public final class WitchbaneOrb extends CardImpl {

    public WitchbaneOrb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // When Witchbane Orb enters the battlefield, destroy all Curses attached to you.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new WitchbaneOrbEffect()));

        // You have hexproof.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControllerEffect(HexproofAbility.getInstance())));

    }

    private WitchbaneOrb(final WitchbaneOrb card) {
        super(card);
    }

    @Override
    public WitchbaneOrb copy() {
        return new WitchbaneOrb(this);
    }
}

class WitchbaneOrbEffect extends OneShotEffect {

    public WitchbaneOrbEffect() {
        super(Outcome.Protect);
        staticText = "destroy all Curses attached to you";
    }

    private WitchbaneOrbEffect(final WitchbaneOrbEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            List<Permanent> toDestroy = new ArrayList<>();
            for (UUID attachmentId : controller.getAttachments()) {
                Permanent attachment = game.getPermanent(attachmentId);
                if (attachment != null && attachment.hasSubtype(SubType.CURSE, game)) {
                    toDestroy.add(attachment);
                }
            }
            for (Permanent curse : toDestroy) {
                curse.destroy(source, game, false);
            }
            return true;
        }
        return false;
    }

    @Override
    public WitchbaneOrbEffect copy() {
        return new WitchbaneOrbEffect(this);
    }

}
