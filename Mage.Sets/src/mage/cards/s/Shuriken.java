package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.UnattachCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityWithAttachmentEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Shuriken extends CardImpl {

    public Shuriken(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has "{tap}, Unattach Shuriken: Shuriken deals 2 damage to target creature. That creature's controller gains control of Shuriken unless it was unattached from a Ninja."
        this.addAbility(new SimpleStaticAbility(new GainAbilityWithAttachmentEffect(
                "equipped creature has \"{T}, Unattach {this}: {this} deals 2 damage to target creature. " +
                        "That creature's controller gains control of {this} unless it was unattached from a Ninja.\"",
                new ShurikenEffect(), new TargetCreaturePermanent(), new UnattachCost(), new TapSourceCost()
        )));

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.PreventDamage, new GenericManaCost(2)));
    }

    private Shuriken(final Shuriken card) {
        super(card);
    }

    @Override
    public Shuriken copy() {
        return new Shuriken(this);
    }
}

class ShurikenEffect extends OneShotEffect {

    ShurikenEffect() {
        super(Outcome.Benefit);
    }

    private ShurikenEffect(final ShurikenEffect effect) {
        super(effect);
    }

    @Override
    public ShurikenEffect copy() {
        return new ShurikenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Object object = getValue("attachedPermanent");
        Player player = game.getPlayer(source.getControllerId());
        Permanent targetedPermanent = game.getPermanent(source.getFirstTarget());
        if (!(object instanceof Permanent) || player == null || targetedPermanent == null) {
            return false;
        }
        Permanent equipment = (Permanent) object;
        targetedPermanent.damage(2, equipment.getId(), source, game);
        Permanent attached = source.getSourcePermanentOrLKI(game);
        if (attached != null && attached.hasSubtype(SubType.NINJA, game)) {
            return true;
        }
        game.addEffect(new GainControlTargetEffect(
                Duration.Custom, true, attached.getControllerId()
        ).setTargetPointer(new FixedTarget(equipment, game)), source);
        return true;
    }

    @Override
    public String getText(Mode mode) {
        String name = "Shuriken";
        Object object = getValue("attachedPermanent");
        if (object instanceof Permanent) {
            name = ((Permanent) object).getName();
        }
        return name + "deals 2 damage to target creature. That creature's controller gains control of "
                + name + " unless it was unattached from a Ninja.";
    }
}
