
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.card.ExpansionSetPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author MarcoMarin
 */
public final class GolgothianSylex extends CardImpl {

    public GolgothianSylex(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        // {1}, {tap}: Each nontoken permanent from the Antiquities expansion is sacrificed by its controller.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GolgothianSylexEffect(), new ManaCostsImpl<>("{1}"));
        ability.addCost(new TapSourceCost());  
        this.addAbility(ability);
    }

    private GolgothianSylex(final GolgothianSylex card) {
        super(card);
    }

    @Override
    public GolgothianSylex copy() {
        return new GolgothianSylex(this);
    }
}

class GolgothianSylexEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(Predicates.and(
                new ExpansionSetPredicate("ATQ"),
                TokenPredicate.FALSE
        ));
    }

    public GolgothianSylexEffect() {
        super(Outcome.Benefit);
        this.staticText = "Each nontoken permanent from the Antiquities expansion is sacrificed by its controller.";
    }

    public GolgothianSylexEffect(final GolgothianSylexEffect effect) {
        super(effect);
    }

    @Override
    public GolgothianSylexEffect copy() {
        return new GolgothianSylexEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, game)) {
             permanent.sacrifice(source, game);
        }
        return true;
    }
}