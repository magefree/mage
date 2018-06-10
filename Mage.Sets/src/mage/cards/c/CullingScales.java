
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

/**
 *
 * @author cg5
 */
public final class CullingScales extends CardImpl {

    private static final FilterPermanent filterNonlandPermanentWithLowestCmc = new FilterNonlandPermanent(
        "nonland permanent with the lowest converted mana cost (<i>If two or more permanents are tied for lowest cost, target any one of them.</i>)"
    );
    static {
        filterNonlandPermanentWithLowestCmc.add(new HasLowestCMCAmongstNonlandPermanentsPredicate());
    }
    
    public CullingScales(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // At the beginning of your upkeep, destroy target nonland permanent with the lowest converted mana cost.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new DestroyTargetEffect(), TargetController.YOU, false);
        ability.addTarget(new TargetPermanent(filterNonlandPermanentWithLowestCmc));
        this.addAbility(ability);
    }

    public CullingScales(final CullingScales card) {
        super(card);
    }

    @Override
    public CullingScales copy() {
        return new CullingScales(this);
    }
    
}

class HasLowestCMCAmongstNonlandPermanentsPredicate implements Predicate<Permanent> {
    
    @Override
    public boolean apply(Permanent input, Game game) {
        FilterPermanent filter = new FilterNonlandPermanent();
        filter.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, input.getConvertedManaCost()));
        return !game.getBattlefield().contains(filter, 1, game);
    }
    
}