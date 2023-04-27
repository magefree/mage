
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author fireshoes
 */
public final class RamunapRuins extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Desert");

    static {
        filter.add(SubType.DESERT.getPredicate());
    }

    public RamunapRuins(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.DESERT);

        // {t}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {t}, Pay 1 life: Add {R}.
        Ability manaAbility = new RedManaAbility();
        manaAbility.addCost(new PayLifeCost(1));
        this.addAbility(manaAbility);

        // {2}{R}{R}, {t}, Sacrifice a Desert: Ramunap Ruins deals 2 damage to each opponent.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamagePlayersEffect(Outcome.Damage, StaticValue.get(2), TargetController.OPPONENT),
                new ManaCostsImpl<>("{2}{R}{R}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(1, 1, filter, true)));
        this.addAbility(ability);
    }

    private RamunapRuins(final RamunapRuins card) {
        super(card);
    }

    @Override
    public RamunapRuins copy() {
        return new RamunapRuins(this);
    }
}
