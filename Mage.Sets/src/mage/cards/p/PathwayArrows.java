
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class PathwayArrows extends CardImpl {

    public PathwayArrows(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has "{2}, {T}: This creature deals 1 damage to target creature. If a colorless creature is dealt damage this way, tap it."
        SimpleActivatedAbility ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PathwayArrowsEffect(), new GenericManaCost(2));
        ability2.addCost(new TapSourceCost());
        ability2.addTarget(new TargetCreaturePermanent());
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(ability2, AttachmentType.EQUIPMENT)));

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(2), false));
    }

    private PathwayArrows(final PathwayArrows card) {
        super(card);
    }

    @Override
    public PathwayArrows copy() {
        return new PathwayArrows(this);
    }
}

class PathwayArrowsEffect extends OneShotEffect {

    public PathwayArrowsEffect() {
        super(Outcome.Benefit);
        this.staticText = "This creature deals 1 damage to target creature. If a colorless creature is dealt damage this way, tap it";
    }

    public PathwayArrowsEffect(final PathwayArrowsEffect effect) {
        super(effect);
    }

    @Override
    public PathwayArrowsEffect copy() {
        return new PathwayArrowsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent targetCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (targetCreature != null) {
                int damageDealt = targetCreature.damage(1, source.getSourceId(), source, game, false, true);
                if (damageDealt > 0 && targetCreature.getColor(game).isColorless()) {
                    targetCreature.tap(source, game);
                }
            }
            return true;
        }
        return false;
    }
}
