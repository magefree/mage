
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.ChooseColorEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterObject;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author cbt33, noxx (Riders of Gavony)
 */
public final class WardSliver extends CardImpl {

    static FilterPermanent filter = new FilterPermanent("chosen color");
    
    
    public WardSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}");
        this.subtype.add(SubType.SLIVER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        
        // As Ward Sliver enters the battlefield, choose a color.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseColorEffect(Outcome.Protect)));

        // All Slivers have protection from the chosen color.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new WardSliverGainAbilityControlledEffect()));
        
    }

    private WardSliver(final WardSliver card) {
        super(card);
    }

    @Override
    public WardSliver copy() {
        return new WardSliver(this);
    }
}

class WardSliverGainAbilityControlledEffect extends ContinuousEffectImpl {

    protected FilterObject<MageObject> protectionFilter;

    public WardSliverGainAbilityControlledEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "all Slivers have protection from the chosen color";
    }

    public WardSliverGainAbilityControlledEffect(final WardSliverGainAbilityControlledEffect effect) {
        super(effect);
        protectionFilter = effect.protectionFilter;
    }

    @Override
    public WardSliverGainAbilityControlledEffect copy() {
        return new WardSliverGainAbilityControlledEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (protectionFilter == null) {
            Permanent permanent = game.getPermanent(source.getSourceId());
            if (permanent != null) {
                ObjectColor color = (ObjectColor) game.getState().getValue(permanent.getId() + "_color");
                if (color != null) {
                    protectionFilter = new FilterObject<>(color.getDescription());
                    protectionFilter.add(new ColorPredicate(color));
                }
            }
        }
        if (protectionFilter != null) {
            for (Permanent perm: game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_ALL_SLIVERS, game)) {
                perm.addAbility(new ProtectionAbility(protectionFilter), source.getSourceId(), game);
            }
            return true;
        }
        return false;
    }

}
