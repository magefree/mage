package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * 
 * @author balazskristof
 */
public final class ArcumsWeathervane extends CardImpl {

    private static final FilterLandPermanent filterSnow = new FilterLandPermanent("snow land");
    private static final FilterLandPermanent filterNonsnow = new FilterLandPermanent("nonsnow basic land");

    static {
        filterSnow.add(SuperType.SNOW.getPredicate());
        filterNonsnow.add(SuperType.BASIC.getPredicate());
        filterNonsnow.add(Predicates.not(SuperType.SNOW.getPredicate()));
    }

    public ArcumsWeathervane(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // {2}, {tap}: Target snow land is no longer snow.
        Ability ability = new SimpleActivatedAbility(new ArcumsWeatherwaneNoLongerSnowEffect(), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(filterSnow));
        this.addAbility(ability);

        // {2}, {tap}: Target nonsnow basic land becomes snow.
        ability = new SimpleActivatedAbility(new ArcumsWeatherwaneBecomeSnowEffect(), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(filterNonsnow));
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
        super(Duration.Custom, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);
        staticText = "Target snow land is no longer snow";
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
        super(Duration.Custom, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);
        staticText = "Target nonsnow basic land becomes snow";
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
