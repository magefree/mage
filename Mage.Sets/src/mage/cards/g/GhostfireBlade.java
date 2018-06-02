
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public final class GhostfireBlade extends CardImpl {

    public GhostfireBlade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+2
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(2, 2)));
        
        // Equip {3}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(3)));

        // Ghostfire Blade's equip ability costs {2} less to activate if it targets a colorless creature.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new InfoEffect("{this}'s equip ability costs {2} less to activate if it targets a colorless creature")));
    }
  @Override
    public void adjustCosts(Ability ability, Game game) {
        if (ability instanceof EquipAbility) {
            Permanent targetCreature = game.getPermanent(ability.getTargets().getFirstTarget());
            if (targetCreature != null && targetCreature.getColor(game).isColorless()) {
                CardUtil.reduceCost(ability, 2);
            }
        }
    }

    public GhostfireBlade(final GhostfireBlade card) {
        super(card);
    }

    @Override
    public GhostfireBlade copy() {
        return new GhostfireBlade(this);
    }
}
