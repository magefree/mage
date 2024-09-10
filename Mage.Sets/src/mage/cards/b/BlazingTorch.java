package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeAttachmentCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesAttachedEffect;
import mage.abilities.effects.common.continuous.GainAbilityWithAttachmentEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author North
 */
public final class BlazingTorch extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Vampires or Zombies");

    static {
        filter.add(Predicates.or(
                SubType.VAMPIRE.getPredicate(),
                SubType.ZOMBIE.getPredicate()
        ));
    }

    public BlazingTorch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature can't be blocked by Vampires or Zombies. (!this is a static ability of the equipment)
        this.addAbility(new SimpleStaticAbility(new CantBeBlockedByCreaturesAttachedEffect(
                Duration.WhileOnBattlefield, filter, AttachmentType.EQUIPMENT
        )));

        // Equipped creature has "{tap}, Sacrifice Blazing Torch: Blazing Torch deals 2 damage to any target."
        this.addAbility(new SimpleStaticAbility(new GainAbilityWithAttachmentEffect(
                "equipped creature has \"{T}, Sacrifice {this}: {this} deals 2 damage to any target.\"",
                new BlazingTorchEffect(), new TargetAnyTarget(), new SacrificeAttachmentCost(), new TapSourceCost()
        )));

        // Equip {1}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(1)));
    }

    private BlazingTorch(final BlazingTorch card) {
        super(card);
    }

    @Override
    public BlazingTorch copy() {
        return new BlazingTorch(this);
    }
}

class BlazingTorchEffect extends OneShotEffect {

    BlazingTorchEffect() {
        super(Outcome.Benefit);
    }

    private BlazingTorchEffect(final BlazingTorchEffect effect) {
        super(effect);
    }

    @Override
    public BlazingTorchEffect copy() {
        return new BlazingTorchEffect(this);
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
                targetedPlayer.damage(2, permanent.getId(), source, game);
            }
        } else {
            targetedPermanent.damage(2, permanent.getId(), source, game);
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        String name = "Blazing Torch";
        Object object = getValue("attachedPermanent");
        if (object instanceof Permanent) {
            name = ((Permanent) object).getName();
        }
        return name + " deals 2 damage to any target.";
    }
}
