
package mage.cards.c;

import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.ChangelingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.SubTypeList;

/**
 *
 * @author North
 */
public final class CoatOfArms extends CardImpl {

    public CoatOfArms(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{5}");

        // Each creature gets +1/+1 for each other creature on the battlefield that shares at least one creature type with it.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CoatOfArmsEffect()));
    }

    public CoatOfArms(final CoatOfArms card) {
        super(card);
    }

    @Override
    public CoatOfArms copy() {
        return new CoatOfArms(this);
    }
}

class CoatOfArmsEffect extends ContinuousEffectImpl {

   public CoatOfArmsEffect() {
        super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
        this.staticText = "Each creature gets +1/+1 for each other creature on the battlefield that shares at least one creature type with it";
    }

    public CoatOfArmsEffect(final CoatOfArmsEffect effect) {
        super(effect);
    }

    @Override
    public CoatOfArmsEffect copy() {
        return new CoatOfArmsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), game);
        for (Permanent permanent : permanents) {
            int amount = getAmount(permanents, permanent, game);
            permanent.addPower(amount);
            permanent.addToughness(amount);
        }
        return true;
    }

    private int getAmount(List<Permanent> permanents, Permanent target, Game game) {
        int amount = 0;
        SubTypeList targetSubtype = target.getSubtype(game);
        if (target.getAbilities().contains(ChangelingAbility.getInstance()) || target.isAllCreatureTypes()) {
            return permanents.size() - 1;
        }
        for (Permanent permanent : permanents) {
            if (!permanent.getId().equals(target.getId())) {
                for (SubType subtype : targetSubtype) {
                    if (subtype.getSubTypeSet() == SubTypeSet.CreatureType) {
                        if (permanent.hasSubtype(subtype, game)) {
                            amount++;
                            break;
                        }
                    }
                }
            }
        }
        return amount;
    }
}
