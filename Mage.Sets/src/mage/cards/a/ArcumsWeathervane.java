package mage.cards.a;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.SuperType;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

/**
 * 
 * @author balazskristof
 */
public final class ArcumsWeathervane extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("snow land");
    private static final FilterLandPermanent filter2 = new FilterLandPermanent("nonsnow basic land");

    static {
        filter.add(SuperType.SNOW.getPredicate());
        filter2.add(SuperType.BASIC.getPredicate());
        filter2.add(Predicates.not(SuperType.SNOW.getPredicate()));
    }

    public ArcumsWeathervane(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");
        

        // {2}, {tap}: Target snow land is no longer snow.
        Ability ability = new SimpleActivatedAbility(new ArcumsWeatherwaneNoLongerSnowEffect(), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);
        // {2}, {tap}: Target nonsnow basic land becomes snow.
        ability = new SimpleActivatedAbility(new ArcumsWeatherwaneBecomeSnowEffect(), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);
    }

    private ArcumsWeathervane(final ArcumsWeathervane card) {
        super(card);
    }

    @Override
    public ArcumsWeathervane copy() {
        return new ArcumsWeathervane(this);
    }
}

class ArcumsWeatherwaneNoLongerSnowEffect extends ContinuousEffectImpl {
    
    ArcumsWeatherwaneNoLongerSnowEffect() {
        super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);
        staticText = "Target snow land is no longer snow.";
    }

    private ArcumsWeatherwaneNoLongerSnowEffect(final ArcumsWeatherwaneNoLongerSnowEffect effect) {
        super(effect);
    }

    @Override
    public ArcumsWeatherwaneNoLongerSnowEffect copy() {
        return new ArcumsWeatherwaneNoLongerSnowEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent land = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (land != null) {
            land.removeSuperType(SuperType.SNOW);
            return true;
        }
        return false;
    }
}

class ArcumsWeatherwaneBecomeSnowEffect extends ContinuousEffectImpl {
    
    ArcumsWeatherwaneBecomeSnowEffect() {
        super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);
        staticText = "Target nonsnow basic land becomes snow.";
    }

    private ArcumsWeatherwaneBecomeSnowEffect(final ArcumsWeatherwaneBecomeSnowEffect effect) {
        super(effect);
    }

    @Override
    public ArcumsWeatherwaneBecomeSnowEffect copy() {
        return new ArcumsWeatherwaneBecomeSnowEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent land = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (land != null) {
            land.addSuperType(SuperType.SNOW);
            return true;
        }
        return false;
    }
}