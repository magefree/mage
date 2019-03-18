
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.UnattachCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author jeffwadsworth
 */
public final class RazorBoomerang extends CardImpl {

    public RazorBoomerang(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has "{tap}, Unattach Razor Boomerang: Razor Boomerang deals 1 damage to any target. Return Razor Boomerang to its owner's hand."
        Ability gainAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new RazorBoomerangEffect(this.getId()), new TapSourceCost());
        gainAbility.addCost(new UnattachCost(this.getName(), this.getId()));
        gainAbility.addTarget(new TargetAnyTarget());
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(gainAbility, AttachmentType.EQUIPMENT)));

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(2)));
    }

    public RazorBoomerang(final RazorBoomerang card) {
        super(card);
    }

    @Override
    public RazorBoomerang copy() {
        return new RazorBoomerang(this);
    }
}

class RazorBoomerangEffect extends OneShotEffect {

    private static String text = "Razor Boomerang deals 1 damage to any target. Return Razor Boomerang to its owner's hand";
    private UUID attachmentid;

    RazorBoomerangEffect(UUID attachmentid) {
        super(Outcome.Damage);
        this.attachmentid = attachmentid;
        staticText = text;
    }

    RazorBoomerangEffect(RazorBoomerangEffect effect) {
        super(effect);
        this.attachmentid = effect.attachmentid;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID target : targetPointer.getTargets(game, source)) {
            Permanent creature = game.getPermanent(target);
            if (creature != null) {
                creature.damage(1, attachmentid, game, false, true);
            }
            Player player = game.getPlayer(target);
            if (player != null) {
                player.damage(1, attachmentid, game, false, true);
            }
        }
        Permanent razor = game.getPermanent(attachmentid);
        if (razor != null) {
            razor.moveToZone(Zone.HAND, source.getSourceId(), game, true);
        }
        return true;
    }

    @Override
    public RazorBoomerangEffect copy() {
        return new RazorBoomerangEffect(this);
    }
}
