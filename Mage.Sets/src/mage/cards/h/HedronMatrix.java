

package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class HedronMatrix extends CardImpl {

    public HedronMatrix (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +X/+X, where X is its mana value.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new HedronMatrixEffect()));

        // Equip {4}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(4), new TargetControlledCreaturePermanent(), false));
    }

    public HedronMatrix (final HedronMatrix card) {
        super(card);
    }

    @Override
    public HedronMatrix copy() {
        return new HedronMatrix(this);
    }

}

class HedronMatrixEffect extends ContinuousEffectImpl {

    public HedronMatrixEffect() {
        super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
        staticText = "Equipped creature gets +X/+X, where X is its mana value";
    }

    public HedronMatrixEffect(final HedronMatrixEffect effect) {
        super(effect);
    }

    @Override
    public HedronMatrixEffect copy() {
        return new HedronMatrixEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent equipment = game.getPermanent(source.getSourceId());
        if (equipment != null && equipment.getAttachedTo() != null) {
            Permanent creature = game.getPermanent(equipment.getAttachedTo());
            if (creature != null) {
                creature.addPower(creature.getManaValue());
                creature.addToughness(creature.getManaValue());
            }
        }
        return true;
    }

}
