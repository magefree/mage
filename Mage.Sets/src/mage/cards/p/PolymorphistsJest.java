
package mage.cards.p;

import java.util.Iterator;
import java.util.UUID;
import mage.MageObjectReference;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public final class PolymorphistsJest extends CardImpl {

    public PolymorphistsJest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}{U}");


        // Until end of turn, each creature target player controls loses all abilities and becomes a blue Frog with base power and toughness 1/1.
        this.getSpellAbility().addEffect(new PolymorphistsJestEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());

    }

    public PolymorphistsJest(final PolymorphistsJest card) {
        super(card);
    }

    @Override
    public PolymorphistsJest copy() {
        return new PolymorphistsJest(this);
    }
}

class PolymorphistsJestEffect extends ContinuousEffectImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    public PolymorphistsJestEffect() {
        super(Duration.EndOfTurn, Outcome.BecomeCreature);
        staticText = "Until end of turn, each creature target player controls loses all abilities and becomes a blue Frog with base power and toughness 1/1";
    }

    public PolymorphistsJestEffect(final PolymorphistsJestEffect effect) {
        super(effect);
    }

    @Override
    public PolymorphistsJestEffect copy() {
        return new PolymorphistsJestEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (this.affectedObjectsSet) {
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, getTargetPointer().getFirst(game, source), game)) {
                affectedObjectList.add(new MageObjectReference(permanent, game));
            }
        }
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        for (Iterator<MageObjectReference> it = affectedObjectList.iterator(); it.hasNext();) {
            Permanent permanent = it.next().getPermanent(game);
            if (permanent != null) {
                switch (layer) {
                    case TypeChangingEffects_4:
                        if (sublayer == SubLayer.NA) {
                            permanent.getSubtype(game).clear();
                            permanent.getSubtype(game).add(SubType.FROG);
                        }

                        break;
                    case ColorChangingEffects_5:
                        if (sublayer == SubLayer.NA) {
                            permanent.getColor(game).setBlack(false);
                            permanent.getColor(game).setGreen(false);
                            permanent.getColor(game).setBlue(false);
                            permanent.getColor(game).setWhite(false);
                            permanent.getColor(game).setBlack(false);
                            permanent.getColor(game).setColor(ObjectColor.BLUE);
                        }
                        break;
                    case AbilityAddingRemovingEffects_6:
                        permanent.removeAllAbilities(source.getSourceId(), game);
                        break;
                    case PTChangingEffects_7:
                        if (sublayer == SubLayer.SetPT_7b) {
                            permanent.getPower().setValue(1);
                            permanent.getToughness().setValue(1);
                        }
                }
            } else {
                it.remove();
            }
        }
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.PTChangingEffects_7 || layer == Layer.ColorChangingEffects_5 || layer == Layer.AbilityAddingRemovingEffects_6 || layer == Layer.TypeChangingEffects_4;
    }

}
