package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeAttachmentCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityWithAttachmentEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NinjasKunai extends CardImpl {

    public NinjasKunai(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has "{1}, {T}, Sacrifice Ninja's Kunai: Ninja's Kunai deals 3 damage to any target."
        this.addAbility(new SimpleStaticAbility(new GainAbilityWithAttachmentEffect(
                "equipped creature has \"{1}, {T}, Sacrifice {this}: {this} deals 2 damage to any target.\"",
                new NinjasKunaiEffect(), new TargetAnyTarget(), new SacrificeAttachmentCost(), new GenericManaCost(1), new TapSourceCost()
        )));

        // Equip {1}
        this.addAbility(new EquipAbility(1));
    }

    private NinjasKunai(final NinjasKunai card) {
        super(card);
    }

    @Override
    public NinjasKunai copy() {
        return new NinjasKunai(this);
    }
}

class NinjasKunaiEffect extends OneShotEffect {

    NinjasKunaiEffect() {
        super(Outcome.Benefit);
    }

    private NinjasKunaiEffect(final NinjasKunaiEffect effect) {
        super(effect);
    }

    @Override
    public NinjasKunaiEffect copy() {
        return new NinjasKunaiEffect(this);
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
                targetedPlayer.damage(3, permanent.getId(), source, game);
            }
        } else {
            targetedPermanent.damage(3, permanent.getId(), source, game);
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        String name = "Ninja's Kunai";
        Object object = getValue("attachedPermanent");
        if (object instanceof Permanent) {
            name = ((Permanent) object).getName();
        }
        return name + "deals 3 damage to target any target.";
    }
}
