package mage.cards.e;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.ChangelingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPlayer;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * @author Styxo
 */
public final class EgoErasure extends CardImpl {

    public EgoErasure(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.TRIBAL, CardType.INSTANT}, "{2}{U}");
        this.subtype.add(SubType.SHAPESHIFTER);

        // Changeling
        this.addAbility(new ChangelingAbility());

        //Creatures target player controls get -2/+0 and lose all creature types until end of turn.
        this.getSpellAbility().addEffect(new EgoErasureBoostEffect());
        this.getSpellAbility().addEffect(new EgoErasureLoseEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private EgoErasure(final EgoErasure card) {
        super(card);
    }

    @Override
    public EgoErasure copy() {
        return new EgoErasure(this);
    }
}

class EgoErasureLoseEffect extends ContinuousEffectImpl {

    public EgoErasureLoseEffect() {
        super(Duration.EndOfTurn, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Neutral);
        staticText = "and lose all creature types until end of turn";
    }

    public EgoErasureLoseEffect(final EgoErasureLoseEffect effect) {
        super(effect);
    }

    @Override
    public EgoErasureLoseEffect copy() {
        return new EgoErasureLoseEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (this.affectedObjectsSet) {
            List<Permanent> creatures = game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, source.getFirstTarget(), game);
            for (Permanent creature : creatures) {
                affectedObjectList.add(new MageObjectReference(creature, game));
            }
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Iterator<MageObjectReference> it = affectedObjectList.iterator(); it.hasNext(); ) {
            Permanent permanent = it.next().getPermanent(game);
            if (permanent != null) {
                permanent.removeAllCreatureTypes(game);
            } else {
                it.remove();
            }
        }
        return true;
    }
}

class EgoErasureBoostEffect extends ContinuousEffectImpl {

    public EgoErasureBoostEffect() {
        super(Duration.EndOfTurn, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.Benefit);
        staticText = "Creatures target player controls get -2/+0";
    }

    public EgoErasureBoostEffect(final EgoErasureBoostEffect effect) {
        super(effect);
    }

    @Override
    public EgoErasureBoostEffect copy() {
        return new EgoErasureBoostEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (this.affectedObjectsSet) {
            List<Permanent> creatures = game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, source.getFirstTarget(), game);
            for (Permanent creature : creatures) {
                affectedObjectList.add(new MageObjectReference(creature, game));
            }
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Iterator<MageObjectReference> it = affectedObjectList.iterator(); it.hasNext(); ) {
            Permanent permanent = it.next().getPermanent(game);
            if (permanent != null) {
                permanent.addPower(-2);
            } else {
                it.remove();
            }
        }
        return true;
    }
}
