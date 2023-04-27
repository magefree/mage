
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import static mage.filter.StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Pete Rossi
 */
public final class SanguinePraetor extends CardImpl {

    public SanguinePraetor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{B}{B}");

        this.subtype.add(SubType.AVATAR);
        this.subtype.add(SubType.PRAETOR);
        this.power = new MageInt(7);
        this.toughness = new MageInt(5);

        // {B}, Sacrifice a creature: Destroy each creature with the same converted mana cost as the sacrificed creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SanguinePraetorEffect(), new ManaCostsImpl<>("{B}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(FILTER_CONTROLLED_CREATURE_SHORT_TEXT)));
        this.addAbility(ability);

    }

    private SanguinePraetor(final SanguinePraetor card) {
        super(card);
    }

    @Override
    public SanguinePraetor copy() {
        return new SanguinePraetor(this);
    }
}

class SanguinePraetorEffect extends OneShotEffect {

    public SanguinePraetorEffect() {
        super(Outcome.Damage);
        staticText = "Destroy each creature with the same mana value as the sacrificed creature";
    }

    public SanguinePraetorEffect(final SanguinePraetorEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int cmc = 0;
        for (Cost cost : source.getCosts()) {
            if (cost instanceof SacrificeTargetCost && !((SacrificeTargetCost) cost).getPermanents().isEmpty()) {
                cmc = ((SacrificeTargetCost) cost).getPermanents().get(0).getManaValue();
                break;
            }
        }

        for (Permanent permanent : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), game)) {
            if (permanent.getManaValue() == cmc) {
                permanent.destroy(source, game, false);
            }
        }
        return true;
    }

    @Override
    public SanguinePraetorEffect copy() {
        return new SanguinePraetorEffect(this);
    }
}
