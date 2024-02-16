package mage.cards.v;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Iterator;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VolatileClaws extends CardImpl {

    public VolatileClaws(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Until end of turn, creatures you control get +2/+0 and gain all creature types.
        this.getSpellAbility().addEffect(new BoostControlledEffect(
                2, 0, Duration.EndOfTurn
        ).setText("until end of turn, creatures you control get +2/+0"));
        this.getSpellAbility().addEffect(new VolatileClawsEffect());
    }

    private VolatileClaws(final VolatileClaws card) {
        super(card);
    }

    @Override
    public VolatileClaws copy() {
        return new VolatileClaws(this);
    }
}

class VolatileClawsEffect extends ContinuousEffectImpl {

    VolatileClawsEffect() {
        super(Duration.EndOfTurn, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);
        staticText = "and gain all creature types";
    }

    private VolatileClawsEffect(final VolatileClawsEffect effect) {
        super(effect);
    }

    @Override
    public VolatileClawsEffect copy() {
        return new VolatileClawsEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        for (Permanent perm : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_CONTROLLED_CREATURES, source.getControllerId(), source, game
        )) {
            affectedObjectList.add(new MageObjectReference(perm, game));
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Iterator<MageObjectReference> it = affectedObjectList.iterator(); it.hasNext(); ) {
            Permanent permanent = it.next().getPermanent(game);
            if (permanent == null) {
                it.remove(); // no longer on the battlefield, remove reference to object
                continue;
            }
            permanent.setIsAllCreatureTypes(game, true);
        }
        return true;
    }
}
