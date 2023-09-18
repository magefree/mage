package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.UnattachCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityWithAttachmentEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 * @author jeffwadsworth
 */
public final class RazorBoomerang extends CardImpl {

    public RazorBoomerang(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has "{tap}, Unattach Razor Boomerang: Razor Boomerang deals 1 damage to any target. Return Razor Boomerang to its owner's hand."
        this.addAbility(new SimpleStaticAbility(new GainAbilityWithAttachmentEffect(
                "equipped creature has \"{T}, Unattach {this}: " +
                        "It deals 1 damage to any target. Return {this} to its owner's hand.\"",
                new RazorBoomerangEffect(), new TargetAnyTarget(),
                new UnattachCost(), new TapSourceCost()
        )));

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(2), new TargetControlledCreaturePermanent(), false));
    }

    private RazorBoomerang(final RazorBoomerang card) {
        super(card);
    }

    @Override
    public RazorBoomerang copy() {
        return new RazorBoomerang(this);
    }
}

class RazorBoomerangEffect extends OneShotEffect {

    RazorBoomerangEffect() {
        super(Outcome.Benefit);
    }

    private RazorBoomerangEffect(final RazorBoomerangEffect effect) {
        super(effect);
    }

    @Override
    public RazorBoomerangEffect copy() {
        return new RazorBoomerangEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Object object = getValue("attachedPermanent");
        Player player = game.getPlayer(source.getControllerId());
        if (!(object instanceof Permanent) || player == null) {
            return false;
        }
        Permanent permanent = (Permanent) object;
        Permanent targetedPermanent = game.getPermanent(source.getFirstTarget());
        if (targetedPermanent == null) {
            Player targetedPlayer = game.getPlayer(source.getFirstTarget());
            if (targetedPlayer != null) {
                targetedPlayer.damage(1, permanent.getId(), source, game);
            }
        } else {
            targetedPermanent.damage(1, permanent.getId(), source, game);
        }
        return player.moveCards(permanent, Zone.HAND, source, game);
    }

    @Override
    public String getText(Mode mode) {
        String name = "Razor Boomerang";
        Object object = getValue("attachedPermanent");
        if (object instanceof Permanent) {
            name = ((Permanent) object).getName();
        }
        return name + " deals 1 damage to any target. Return " + name + " to its owner's hand.";
    }
}
