package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author Loki
 */
public final class HedronMatrix extends CardImpl {

    public HedronMatrix (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +X/+X, where X is its mana value.
        this.addAbility(new SimpleStaticAbility(new HedronMatrixEffect()));

        // Equip {4}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(4), new TargetControlledCreaturePermanent(), false));
    }

    private HedronMatrix(final HedronMatrix card) {
        super(card);
    }

    @Override
    public HedronMatrix copy() {
        return new HedronMatrix(this);
    }

}

class HedronMatrixEffect extends ContinuousEffectImpl {

    HedronMatrixEffect() {
        super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
        staticText = "Equipped creature gets +X/+X, where X is its mana value";
    }

    private HedronMatrixEffect(final HedronMatrixEffect effect) {
        super(effect);
    }

    @Override
    public HedronMatrixEffect copy() {
        return new HedronMatrixEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getPermanentSourceAttachedToIfItStillExists(game);
        if (permanent == null) {
            return false;
        }
        permanent.addPower(permanent.getManaValue());
        permanent.addToughness(permanent.getManaValue());
        return true;
    }

}
