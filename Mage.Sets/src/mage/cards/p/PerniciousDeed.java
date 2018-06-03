
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author Plopman
 */
public final class PerniciousDeed extends CardImpl {


    public PerniciousDeed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}{G}");


        // {X}, Sacrifice Pernicious Deed: Destroy each artifact, creature, and enchantment with converted mana cost X or less.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PerniciousDeedEffect(), new VariableManaCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    public PerniciousDeed(final PerniciousDeed card) {
        super(card);
    }

    @Override
    public PerniciousDeed copy() {
        return new PerniciousDeed(this);
    }
}


class PerniciousDeedEffect extends OneShotEffect {


    public PerniciousDeedEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "Destroy each artifact, creature, and enchantment with converted mana cost X or less";
    }


    public PerniciousDeedEffect(final PerniciousDeedEffect effect) {
        super(effect);
    }

    @Override
    public PerniciousDeedEffect copy() {
        return new PerniciousDeedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {


        FilterPermanent filter = new FilterPermanent("artifacts, creatures, and enchantments");


        filter.add(Predicates.or(
                new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.CREATURE),
                new CardTypePredicate(CardType.ENCHANTMENT)));
        filter.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, source.getManaCostsToPay().getX() + 1));


        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
            permanent.destroy(source.getSourceId(), game, false);
        }
        return true;
    }

}