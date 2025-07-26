
package mage.cards.c;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.List;
import java.util.UUID;

/**
 * @author North
 */
public final class CoatOfArms extends CardImpl {

    public CoatOfArms(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");

        // Each creature gets +1/+1 for each other creature on the battlefield that shares at least one creature type with it.
        this.addAbility(new SimpleStaticAbility(new CoatOfArmsEffect()));
    }

    private CoatOfArms(final CoatOfArms card) {
        super(card);
    }

    @Override
    public CoatOfArms copy() {
        return new CoatOfArms(this);
    }
}

class CoatOfArmsEffect extends ContinuousEffectImpl {

    CoatOfArmsEffect() {
        super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
        this.staticText = "Each creature gets +1/+1 for each other creature on the battlefield that shares at least one creature type with it";
    }

    private CoatOfArmsEffect(final CoatOfArmsEffect effect) {
        super(effect);
    }

    @Override
    public CoatOfArmsEffect copy() {
        return new CoatOfArmsEffect(this);
    }

    @Override
    public void applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> affectedObjects) {
        for (MageItem object : affectedObjects) {
            Permanent permanent = (Permanent) object;
            int amount = getAmount(affectedObjects, permanent, game);
            permanent.addPower(amount);
            permanent.addToughness(amount);
        }
    }

    @Override
    public boolean queryAffectedObjects(Layer layer, Ability source, Game game, List<MageItem> affectedObjects) {
        affectedObjects.addAll(game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), game));
        return !affectedObjects.isEmpty();
    }

    private int getAmount(List<MageItem> permanents, Permanent target, Game game) {
        int amount = 0;
        for (MageItem permanent : permanents) {
            if (!permanent.getId().equals(target.getId()) && ((Permanent) permanent).shareCreatureTypes(game, target)) {
                amount++;
            }
        }
        return amount;
    }
}
