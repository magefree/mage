
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

/**
 *
 * @author anonymous
 */
public final class GriffinCanyon extends CardImpl {
    
    private static final FilterPermanent filter = new FilterPermanent(SubType.GRIFFIN, "target Griffin");
            
    public GriffinCanyon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
        // {tap}: Untap target Griffin. If it's a creature, it gets +1/+1 until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GriffinCanyonEffect(), new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private GriffinCanyon(final GriffinCanyon card) {
        super(card);
    }

    @Override
    public GriffinCanyon copy() {
        return new GriffinCanyon(this);
    }
}

class GriffinCanyonEffect extends OneShotEffect {

    public GriffinCanyonEffect() {
        super(Outcome.Benefit);
        this.staticText = "Untap target Griffin. If it's a creature, it gets +1/+1 until end of turn";
    }

    public GriffinCanyonEffect(final GriffinCanyonEffect effect) {
        super(effect);
    }

    @Override
    public GriffinCanyonEffect copy() {
        return new GriffinCanyonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetCreature = game.getPermanent(source.getFirstTarget());
        if (targetCreature != null) {
            targetCreature.untap(game);
            if (StaticFilters.FILTER_PERMANENT_A_CREATURE.match(targetCreature, game)) {
                game.addEffect(new BoostTargetEffect(1, 1, Duration.EndOfTurn), source);
            }
            return true;
        }
        return false;
    }
}