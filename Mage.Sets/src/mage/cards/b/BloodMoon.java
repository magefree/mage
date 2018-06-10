
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public final class BloodMoon extends CardImpl {

    public BloodMoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{R}");

        // Nonbasic lands are Mountains.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BloodMoonEffect()));
    }

    public BloodMoon(final BloodMoon card) {
        super(card);
    }

    @Override
    public BloodMoon copy() {
        return new BloodMoon(this);
    }
}

class BloodMoonEffect extends ContinuousEffectImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent();

    static {
        filter.add(Predicates.not(new SupertypePredicate(SuperType.BASIC)));
    }

    BloodMoonEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        this.staticText = "Nonbasic lands are Mountains";
    }

    BloodMoonEffect(final BloodMoonEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public BloodMoonEffect copy() {
        return new BloodMoonEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        for (Permanent land : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game)) {
            switch (layer) {
                case TypeChangingEffects_4:
                    // 305.7 Note that this doesn't remove any abilities that were granted to the land by other effects
                    // So the ability removing has to be done before Layer 6
                    land.removeAllAbilities(source.getSourceId(), game);
                    land.getSubtype(game).removeAll(SubType.getLandTypes(false));
                    land.getSubtype(game).add(SubType.MOUNTAIN);
                    break;
                case AbilityAddingRemovingEffects_6:
                    land.addAbility(new RedManaAbility(), source.getSourceId(), game);
                    break;
            }
        }
        return true;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.AbilityAddingRemovingEffects_6 || layer == Layer.TypeChangingEffects_4;
    }
}
